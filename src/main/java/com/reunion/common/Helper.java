package com.reunion.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import com.reunion.model.Groupe;
import com.reunion.model.Membre;
import com.reunion.model.Trafique;

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

	public static boolean coherance(List<Trafique> trafiquesDelaNouvelleRonde, List<Integer> listeDesRangs) {
		List<Integer> helper = new ArrayList<>();
		if (trafiquesDelaNouvelleRonde == null || listeDesRangs == null || trafiquesDelaNouvelleRonde.isEmpty()
				|| listeDesRangs.isEmpty())
			return false;
		for (Trafique trafique : trafiquesDelaNouvelleRonde) {
			if (!helper.contains(trafique.getRang())) {
				helper.add(trafique.getRang());
			} else {
				return false;
			}
		}
		return true;
	}

	public static List<Membre> trouveLesMembreDuGroupe(List<Membre> listeDesMembres, Groupe groupe) {
		listeDesMembres =  listeDesMembres.stream().filter(membre->{
			return (membre.getGroupe() != null)?membre.getGroupe().getId() == groupe.getId():false;
		}).collect(Collectors.toList());
		return listeDesMembres;
	}

}
