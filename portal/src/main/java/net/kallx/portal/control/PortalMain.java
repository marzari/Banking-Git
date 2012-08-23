package net.kallx.portal.control;

import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import net.kallx.applications.control.PortalCentral;
import net.kallx.applications.model.PortalApplication;

@Startup
@Singleton
public class PortalMain implements Serializable {
	
	@EJB
	PortalCentral  manager = null;
	
	@PostConstruct
	void prepare(){
		
		//TODO
//		if (manager == null) return;
//		
//		URL resource = Thread.currentThread().getContextClassLoader().getResource("pmconf");
//		
//		Path path = null;
//		
//		try {
//			path = Paths.get(resource.getFile().substring(1));
//		} catch (Exception e) {e.printStackTrace();}
//		
//		if (path == null || !path.toFile().exists())
//			try {
//				path = Paths.get(resource.getFile());
//			} catch (Exception e) {e.printStackTrace();}
//		
//		if (path == null || !path.toFile().exists()){

			PortalApplication a = new PortalApplication("banking", "Banking", "resources/images/bankingIcon.png", "banking.xhtml");
			PortalApplication b = new PortalApplication("budget", "Budget", "resources/images/budgetIcon.png", "budget.xhtml");
			PortalApplication c = new PortalApplication("central", "Central", "resources/images/centralIcon.png", "central.xhtml");
			PortalApplication d = new PortalApplication("chimpanzee", "Chimpanzee", "resources/images/chimpanzeeIcon.png", "chimpanzee.xhtml");
			PortalApplication e = new PortalApplication("kangaroo", "Kangaroo", "resources/images/kangarooIcon.png", "kangaroo.xhtml");
			PortalApplication f = new PortalApplication("licenser", "Licenser", "resources/images/licenserIcon.png", "licenser.xhtml");
			PortalApplication g = new PortalApplication("procurement", "Procurement", "resources/images/procurementIcon.png", "procurement.xhtml");
			PortalApplication h = new PortalApplication("security", "Security", "resources/images/securityIcon.png", "security.xhtml");
			PortalApplication i = new PortalApplication("zebra", "Zebra", "resources/images/zebraIcon.png", "zebra.xhtml");
			
			manager.loadApp(a, b, c, d, e, f, g, h, i);
			
//		}
//		
//		if (!path.toFile().exists())
//			path = Paths.get(resource.getFile());
//		
//		 loading presentation packages (jars)
//		try {
//			List<String> paths = new ArrayList<>();
//			Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources("META-INF/kx-presentation.xml");
//			while (resources.hasMoreElements())
//				paths.add(resources.nextElement().getFile().substring(1));
//			extractSections(paths, path.getParent().getParent().resolve("jsf"));
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//
//		 loading app conf xml
//		for (File file : path.toFile().listFiles()) {
//			if (file.getName().endsWith("kxapp.xml"))
//				try {
//					manager.loadAppStream(new FileInputStream(file));
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				}
//		}
			
	}
	
	private void extractSections(List<String> paths, Path base){
		
		for (String string : paths) {
			extractSingleSection(string, base);
		}
		
	}
	
	private void extractSingleSection(String path, Path base){
		
		try (JarInputStream jis = new JarInputStream(Files.newInputStream(Paths.get(path).getParent().getParent()))) {

			int n;
			byte[] buf = new byte[1024];

			ZipEntry entry;
			while ((entry = jis.getNextEntry()) != null) {

				if (!entry.getName().startsWith("sections"))
					continue;

				if (entry.isDirectory()) {
					Files.createDirectories(base.resolve(entry.getName()));
				} else {
					Path newFile = Files.createFile(base.resolve(entry.getName()));
					OutputStream nos = Files.newOutputStream(newFile);
					while ((n = jis.read(buf, 0, 1024)) > -1)
						nos.write(buf, 0, n);

					nos.close();
				}

				jis.closeEntry();
			}

		} catch (Exception e3) {
			e3.printStackTrace();
		}
		
	}
	
	
}