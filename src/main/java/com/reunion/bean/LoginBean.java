package com.reunion.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reunion.business.LoginService;
import com.reunion.common.Pages;
import com.reunion.model.Membre;

@Named
@ConversationScoped
public class LoginBean extends BasicBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LoginService loginService;
	private String email;
	private String motDepass;
	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	private boolean registered;
	
	public void init() {
		loginService.startConversation();
		LOG.info("Coversation déclachée");
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
