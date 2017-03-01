package com.reunion.common;

import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class Helper {

	public static UIComponent findComponent(String id) {
		FacesContext context = FacesContext.getCurrentInstance(); 
	    UIViewRoot root = context.getViewRoot();
	    if (id.equals(root.getId()))
	        return root;
	    
	      UIComponent kid = null;
	      UIComponent result = null;
	      Iterator kids = root.getFacetsAndChildren();
	      while (kids.hasNext() && (result == null)) {
	        kid = (UIComponent) kids.next();
	        if (id.equals(kid.getId())) {
	          result = kid;
	          break;
	        }
	        result = findComponent(id);
	        if (result != null) {
	          break;
	        }
	      }
	      return result;
	}
	
	public static String[] valeurEntreeDansLeChamp(UIComponent components, String nom) {
		UIInput uiInput = (UIInput) components.findComponent("motDePass");
		String valeur = uiInput.getLocalValue() == null ? "" : uiInput.getLocalValue().toString();
		String id = uiInput.getClientId();
		String[] result = new String[2];
		result[0] = id;
		result[1] = valeur;
		return result;
	}
}
