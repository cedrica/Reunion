package com.reunion.helper;

import java.util.HashSet;

import com.reunion.model.Adresse;
import com.reunion.model.Contact;
import com.reunion.model.Groupe;
import com.reunion.model.Membre;
import com.reunion.model.Trafique;

public class ModelInitializer {
	
	public static Membre initMembre(){
		Membre membre = new Membre();
		membre.setAdresse(new Adresse());
		membre.setContact(new Contact());
		Groupe groupe = new Groupe();
		groupe.setMembres(new HashSet<Membre>());
		membre.setGroupe(groupe);
		membre.setTrafique(new Trafique());
		return membre;
	}
}	
