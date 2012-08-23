package net.kallx.banking.account.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import net.kallx.banking.account.bean.OperationAccountBean;

@FacesConverter("accountConverter")
public class AccountConverter implements Converter {

	private static Map<Long, OperationAccountBean<?>> mapped = new HashMap<Long, OperationAccountBean<?>>();
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String submittedValue) {
	
		if (submittedValue.trim().equals("")) {  
            return null;  
        } else {  
            try {  
                long number = Long.parseLong(submittedValue);  
                
                if (mapped.containsKey(number))
    				return mapped.get(number);  
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid company"));  
            }  
        }  
  
        return null;  
		
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		
		if (value == null || value.equals("")) {  
            return "";  
        } else {  
        	OperationAccountBean<?> bean = (OperationAccountBean<?>) value;
    		mapped.put(bean.getId(), bean);
            return String.valueOf(bean.getId());  
        }
		
	}

}
