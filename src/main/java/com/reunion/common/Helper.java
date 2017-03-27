package com.reunion.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import com.reunion.model.Membre;

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
		UIInput uiInput = (UIInput) components.findComponent(nom);
		String valeur = uiInput.getLocalValue() == null ? "" : uiInput.getLocalValue().toString();
		String id = uiInput.getClientId();
		String[] result = new String[2];
		result[0] = id;
		result[1] = valeur;
		return result;
	}

	public static void showError(String message, String id) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "title"));
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(id);
	}

	public static void envoieEmail(String auteur, List<String> destinateurs, String titre, String message) {

	}

	public static boolean coherance(List<Membre> membresDelaNouvelleRonde, List<Integer> listeDesRangs) {
		List<Integer> helper = new ArrayList<>();
		if (membresDelaNouvelleRonde == null || listeDesRangs == null || membresDelaNouvelleRonde.isEmpty()
				|| listeDesRangs.isEmpty())
			return false;
		for (Membre membre : membresDelaNouvelleRonde) {
			if (!helper.contains(membre.getTrafique().getRang())) {
				helper.add(membre.getTrafique().getRang());
			} else {
				return false;
			}
		}
		return true;
	}
}
