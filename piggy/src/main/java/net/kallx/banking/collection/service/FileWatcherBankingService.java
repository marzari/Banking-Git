package net.kallx.banking.collection.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.kallx.architecture.repository.PersistenceWrapper;
import net.kallx.banking.collection.model.Collection;
import net.kallx.chimpanzee.components.watcher.FileWatcher;
import net.kallx.chimpanzee.components.watcher.FileWatcherEvent;
import net.kallx.chimpanzee.components.watcher.FileWatcherExecutor;
import net.kallx.chimpanzee.components.watcher.FileWatcherService;
import net.kallx.collection.delivery.CollectionAdapterSet;
import net.kallx.kangaroo.delivery.facade.KangarooDeliveryModuleFacade;
import net.kallx.kangaroo.delivery.service.DeliveryException;
import net.kallx.kangaroo.document.facade.KangarooDocumentModuleFacade;
import net.kallx.kangaroo.document.model.LayoutInstance;
import net.kallx.kangaroo.document.service.ImportReport;
import net.kallx.utils.string.StringUtils;

@Singleton
@Startup
public class FileWatcherBankingService {
	
	@EJB
	private FileWatcherService fileWatcher;
	
	@PostConstruct
	public void init(){
		
		FileWatcher fwb = new FileWatcher();
		fwb.setPath("C:\\kallx\\banking\\");
		fwb.setMask("layout");
		fwb.addEvents(FileWatcherEvent.CREATE);
		fwb.getExecutors().add(new BankingFileImporterExecutor());
		
		try {
			fileWatcher.register(fwb);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			fileWatcher.start();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		
	}

}
class BankingFileImporterExecutor implements FileWatcherExecutor {

	private Set<String> paths;

	public BankingFileImporterExecutor() {
		paths = new HashSet<>();
	}
	
	@Override
	public void execute(FileWatcherEvent event, Path path) {
	
		synchronized (paths) {
			if (paths.contains(path.toFile().getAbsolutePath()))
				return;
			
			paths.add(path.toFile().getAbsolutePath());
		}
		
		try {
			execute(path);
		} catch (NamingException | IOException | DeliveryException e) {
			e.printStackTrace();
		}
		
		synchronized (paths) {
			paths.remove(path.toFile().getAbsolutePath());
		}
		
	}
	
	private void execute(Path path) throws NamingException, IOException, DeliveryException {
		
		String fileName = path.toFile().getName();
		String[] split = fileName.split("\\$");
		String clientName = split[0];
		String layoutName = split[1].replace(".layout", "");
		
		KangarooDocumentModuleFacade kangarooFacade = InitialContext.doLookup("java:global/kallx-application/kangaroo-document/KangarooDocumentModuleFacadeImpl");
		InputStream newInputStream = Files.newInputStream(path);
		ImportReport ir = kangarooFacade.doImport(layoutName, StringUtils.convertStreamToString(newInputStream));
		
		KangarooDeliveryModuleFacade deliveryFacade = InitialContext.doLookup("java:global/kallx-application/kangaroo-delivery/KangarooDeliveryModuleFacadeImpl");
		List<LayoutInstance> tosend = new ArrayList<>();
		tosend.add(ir.getReferentialInstance());
		
		Set<CollectionAdapterSet> delivery = deliveryFacade.processDelivery(CollectionAdapterSet.class, tosend);
		CollectionPopulator populator = new CollectionPopulator();
		List<Collection> populated = populator.populateToModel(new ArrayList<>(delivery));
		
		PersistenceWrapper wrapper = InitialContext.doLookup("java:global/kallx-application/architecture/PersistenceWrapper");
		
		for (Collection collection : populated) {
			wrapper.saveOrUpdate(collection);
		}
		
	}
	
}