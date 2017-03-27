package com.reunion.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reunion.business.LoginService;
import com.reunion.business.MembreService;
import com.reunion.common.Pages;
import com.reunion.helper.Data;
import com.reunion.model.Adresse;
import com.reunion.model.Contact;
import com.reunion.model.Membre;
import com.reunion.model.Trafique;

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
	
	private String email;
	private String motDepass;
	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	private boolean registered;
	
	public void init() {
		loginService.startConversation();
		membreService.startConversation();
		LOG.info("Coversation déclachée");
		List<Membre> membres = membreService.findAll();
		if(membres == null || membres.isEmpty()){
			Membre membre = new Membre();
			membre.setActiver(true);
			membre.setMotDePass("a");
			membre.setNom("Leumaleu");
			membre.setPrenom("Cedric");
			
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
			
			membreService.createMembre(membre);
			membreService.startConversation();
			
			membre = new Membre();
			membre.setActiver(true);
			membre.setMotDePass("b");
			membre.setNom("Kemoue");
			membre.setPrenom("Silas");

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
			
			membreService.createMembre(membre);
			membreService.startConversation();
			
			membre = new Membre();
			membre.setActiver(true);
			membre.setMotDePass("c");
			membre.setNom("Komge");
			membre.setPrenom("marc");

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
			
			membreService.createMembre(membre);
			membreService.startConversation();
		}

			
	}

	
	
	public boolean isRegistered() {
		return registered;
	}



	public void setRegistered(boolean registered) {
		this.registered = registered;
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

	public String verificationDePermission() throws IOException {
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		if (externalContext.getSessionMap().get("email") == null || request.getSession(false) == null) {
			// Session has been invalidated during the previous request.
			setRegistered(false);
			return Pages.INDEX;
		}
		return "";
	}

	public String seConnecter() throws IOException {
		loginService.stopperLaConversation();
		Membre membre = loginService.findMembreByEmail(email.trim());
		if (membre != null && membre.getMotDePass().equals(motDepass)) {
			externalContext.getSessionMap().put("email", email);
			externalContext.getSessionMap().put("motDepass", motDepass);
//			response.sendRedirect("/Reunion/faces/reglement-interieur.xhtml");
//			externalContext.redirect(externalContext.getRequestContextPath() + "/reglement-interieur.xhtml");
			setRegistered(true);
			return Pages.REGLEMENT_INTERIEUR;
		}
		setRegistered(false);
		return Pages.INDEX;
	}

	public String logout() throws IOException {
		externalContext.getSessionMap().remove("email", email);
		externalContext.invalidateSession();
		setRegistered(false);
	    externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
	    return Pages.INDEX;
	}
}
