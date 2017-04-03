package com.reunion.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reunion.business.GroupeService;
import com.reunion.business.LoginService;
import com.reunion.business.MembreService;
import com.reunion.common.Pages;
import com.reunion.model.Adresse;
import com.reunion.model.Contact;
import com.reunion.model.Groupe;
import com.reunion.model.Membre;
import com.reunion.util.SessionUtil;

@Named
@ConversationScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LoginService loginService;
	@Inject
	private MembreService membreService;
	@Inject
	private GroupeService groupeService;
	
	private String email;
	private String motDepass;
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	private Groupe groupe;
	private Membre membreActuel;
	public void init() {
		loginService.startConversation();
		membreService.startConversation();
		groupeService.startConversation();
		LOG.info("Coversation déclachée");
		List<Membre> membres = membreService.findAll();
		if(membres == null || membres.isEmpty()){
			Membre membre = new Membre();
			membre.setMotDePass("a");
			membre.setNom("Leumaleu");
			membre.setPrenom("Cedric");

			membre.setFondDeCaisse(100);
			Adresse adresse = new Adresse();
			adresse.setNumero("127");
			adresse.setPlz(90441);
			adresse.setRue("Schweinauer Hauptstrasse");
			adresse.setVille("Nürnberg");
			membre.setAdresse(adresse);
			
			Contact contact = new Contact();
			contact.setEmail("djikeussi2001@yahoo.fr");
			contact.setTelephone("017663112957");
			membre.setContact(contact);
			
			Groupe groupe = new Groupe();
			groupe.setNom("Erlangen");
			groupe = groupeService.createGroupe(groupe);
			
			membreService.startConversation();
			membre.setGroupe(groupe);
			membre = membreService.createMembre(membre);
			
			
			membre = new Membre();
			membre.setMotDePass("b");
			membre.setNom("Kemoue");
			membre.setPrenom("Silas");

			membre.setFondDeCaisse(100);
			adresse = new Adresse();
			adresse.setNumero("127");
			adresse.setPlz(90441);
			adresse.setRue("Bissingerstrasse");
			adresse.setVille("Erlangen");
			membre.setAdresse(adresse);
			
			contact = new Contact();
			contact.setEmail("silas@yahoo.fr");
			contact.setTelephone("017663113357");
			membre.setContact(contact);

			membre.setGroupe(groupe);
			membre = membreService.createMembre(membre);
			membreService.startConversation();
			
			membre = new Membre();
			membre.setMotDePass("c");
			membre.setNom("Komge");
			membre.setPrenom("marc");
			membre.setFondDeCaisse(100);

			adresse = new Adresse();
			adresse.setNumero("127");
			adresse.setPlz(90441);
			adresse.setRue("Manfredstrasse");
			adresse.setVille("Erlangen");
			membre.setAdresse(adresse);
			
			contact = new Contact();
			contact.setEmail("marc@yahoo.fr");
			contact.setTelephone("017345113357");
			membre.setContact(contact);

			membre.setGroupe(groupe);
			membre = membreService.createMembre(membre);
			groupe.getMembres().add(membre);
		}

			
	}
	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}



	public Groupe getGroupe() {
		return this.groupe;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotDepass() {
		return motDepass;
	}

	public void setMotDepass(String motDepass) {
		this.motDepass = motDepass;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public Membre getMembreActuel() {
		return membreActuel;
	}
	
	public void setMembreActuel(Membre membreActuel) {
		this.membreActuel = membreActuel;
	}
	
	public String seConnecter() throws IOException {
		loginService.stopperLaConversation();
		membreActuel = loginService.findMembreByEmail(email.trim());
		if (membreActuel != null && membreActuel.getMotDePass().equals(motDepass)) {
			HttpSession session = SessionUtil.getSession();
			session.setAttribute(SessionUtil.MEMBRE_ACTUEL, membreActuel);
			setGroupe(membreActuel.getGroupe());
			return Pages.REGLEMENT_INTERIEUR;
		}
		return Pages.INDEX;
	}

	public String logout() throws IOException {
		HttpSession session = SessionUtil.getSession();
		session.removeAttribute(SessionUtil.MEMBRE_ACTUEL);
		session.invalidate();
	    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
	    return Pages.INDEX;
	}

	
	public String ouvreProfile() throws IOException{
		FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/profile.xhtml");
		return Pages.PROFILE;
	}
	
	public String nomDuMembre(){
		HttpSession session = SessionUtil.getSession();
		membreActuel = (Membre) session.getAttribute(SessionUtil.MEMBRE_ACTUEL);
		return membreActuel.getPrenom()+" "+membreActuel.getNom();
	}

}
