package com.reunion.common;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;

public class Helper {

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
