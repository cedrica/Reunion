package com.reunion.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reunion.dao.StaticDAO;
import com.reunion.model.Membre;

@RequestScoped
@ManagedBean
public class MembreConverter implements Converter{
	@Inject 
	private StaticDAO staticDAO;
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		String[] parts = value.split(":");
		if (parts.length == 2) {
			Object object = staticDAO.findById(Long.valueOf(parts[1]), Membre.class);
			return object;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		if (obj == null) {
			return null;
		}

		try {
			return obj.getClass().getName() + ':' + obj.getClass().getMethod("getId").invoke(obj);
		} catch (Exception ex) {
			LOG.warn("Could not get Id from " + obj);
		}

		return obj.getClass().getName();
	}

}
