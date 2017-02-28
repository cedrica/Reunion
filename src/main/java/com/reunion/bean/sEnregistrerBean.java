package com.reunion.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reunion.business.MembreService;
import com.reunion.common.Helper;
import com.reunion.helper.ModelInitializer;
import com.reunion.model.Membre;

@Named
@ConversationScoped
public class sEnregistrerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	MembreService membreService;

	private List<Membre> allMembers;
	private Membre membre;
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	public void init() {
		membreService.startConversation();
		membre = ModelInitializer.initMembre();
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}

	public void save() throws IOException {
		Membre m = membreService.createMembre(membre);
		if (m != null) {
			LOG.debug("Le membre " + m.getNom() + " a été creer");
		}
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect(externalContext.getRequestContextPath() + "/reglement-interieur.xhtml");
	}

	public List<Membre> getAllMembers() {
		allMembers = membreService.findAll();
		return allMembers;
	}

	public void setAllMembers(List<Membre> allMembers) {
		this.allMembers = allMembers;
	}

	public void validerLesChamps(ComponentSystemEvent event) {

		FacesContext fc = FacesContext.getCurrentInstance();

		UIComponent components = event.getComponent();

		// get password
		String[] donnee = Helper.valeurEntreeDansLeChamp(components, "motDePass");
		String motDePass = donnee[1];
		String passwordId = donnee[0];

		// get confirm password
		UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmation");
		String confirmation = uiInputConfirmPassword.getLocalValue() == null ? ""
				: uiInputConfirmPassword.getLocalValue().toString();

		// Let required="true" do its job.
		if (motDePass.isEmpty() || confirmation.isEmpty()) {
			return;
		}

		if (!motDePass.equals(confirmation)) {

			FacesMessage msg = new FacesMessage("Password must match confirm password");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(passwordId, msg);
			fc.renderResponse();

		}

	}

}
