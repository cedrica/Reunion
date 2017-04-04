package com.reunion.helper;

import java.util.HashSet;

import com.reunion.model.Adresse;
import com.reunion.model.Contact;
import com.reunion.model.DonneesBanquaire;
import com.reunion.model.Emprunt;
import com.reunion.model.Groupe;
import com.reunion.model.Membre;

public class ModelInitializer {
	
	public static Membre initMembre(){
		Membre membre = new Membre();
		membre.setAdresse(new Adresse());
		membre.setContact(new Contact());
		Groupe groupe = new Groupe();
		groupe.setMembres(new HashSet<Membre>());
		membre.setDonneesBanquaire(new DonneesBanquaire());
		membre.setGroupe(groupe);
		return membre;
	}
	
	public static Emprunt initEmprunt(){
		Emprunt emprunt = new Emprunt();
		emprunt.setMembre(new Membre());
		return emprunt;
	}
}	
