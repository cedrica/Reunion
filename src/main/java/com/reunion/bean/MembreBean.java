package com.reunion.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reunion.business.MembreService;
import com.reunion.common.Pages;
import com.reunion.helper.ModelInitializer;
import com.reunion.model.Membre;

@Named
@ConversationScoped
public class MembreBean extends AbstractModalBean<Membre> implements Serializable {

	@Inject
	MembreService membreService;

	private List<Membre> allMembers;
	private Membre membre;
	private static final long serialVersionUID = 1L;
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	public void init() {
		membreService.startConversation();
		if (membre == null)
			membre = ModelInitializer.initMembre();
		LOG.info("MemberBean a été initialiser");
		if (allMembers == null) {
			allMembers = membreService.findAll();
		}
	}

	public void validatePassword(ComponentSystemEvent event) {

		FacesContext fc = FacesContext.getCurrentInstance();

		UIComponent components = event.getComponent();

		// get password
		UIInput uiInputPassword = (UIInput) components.findComponent("motDePass");
		String password = uiInputPassword.getLocalValue() == null ? "" : uiInputPassword.getLocalValue().toString();
		String passwordId = uiInputPassword.getClientId();

		// get confirm password
		UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmerMotDepass");
		String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
				: uiInputConfirmPassword.getLocalValue().toString();

		// Let required="true" do its job.
		if (password.isEmpty() || confirmPassword.isEmpty()) {
			return;
		}

		if (!password.equals(confirmPassword)) {

			FacesMessage msg = new FacesMessage("le mot de passe doit correspondre à sa confirmation");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(passwordId, msg);
			fc.renderResponse();

		}

	}

	public String save() {
		// if (!motDePassValide()) {
		//
		// return Pages.SELF;
		// }
		membre.setActiver(true);
		Membre m = membreService.createMembre(membre);
		if (m != null) {
			LOG.debug("Le membre " + m.getNom() + " " + m.getPrenom() + " a été creer");
		}
		return Pages.MEMBRES;
	}

	private boolean motDePassValide() {
		UIInput field1 = (UIInput) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("creerMembre:motDePass");
		UIInput field2 = (UIInput) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("creerMembre:confirmerMotDepass");
		String password = (String) field1.getValue();
		String confPassword = (String) field2.getValue();
		return password.equals(confPassword);
	}

	public String close() {
		setShowModal(false);
		return Pages.MEMBRES;
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}

	public List<Membre> getAllMembers() {
		return allMembers;
	}

	public void setAllMembers(List<Membre> allMembers) {
		this.allMembers = allMembers;
	}

	public String delete(Long id) {
		membreService.setMembreActiv(id, false);
		return Pages.MEMBRES;
	}
}
