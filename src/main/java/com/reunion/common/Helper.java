package com.reunion.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import com.reunion.model.Groupe;
import com.reunion.model.Membre;
import com.reunion.model.Trafique;

public class Helper {


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

	public static void saveImageLocaly(byte[] image, String imageName) {
		try {
			File file = new File(System.getProperty("user.dir") +  "/images");
			if(!file.exists()){
				file.mkdirs();
			}
			String path = System.getProperty("user.dir") +  "/images/" +  imageName;
			path.replace("\\", "/");
			System.out.println("path Save: "+path);
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(image);
			fos.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
