package com.reunion.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reunion.business.GroupeService;
import com.reunion.business.MembreService;
import com.reunion.common.Helper;
import com.reunion.common.Pages;
import com.reunion.helper.ModelInitializer;
import com.reunion.model.Groupe;
import com.reunion.model.Membre;

@Named
@ConversationScoped
public class MembreBean implements Serializable {

	@Inject
	private MembreService membreService;

	@Inject
	private GroupeService groupeService;

	private List<Membre> allMembers;
	private List<Groupe> groupes;
	private Membre membre;
	private static final long serialVersionUID = 1L;
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	private String motDePassConfirm;

	public void init() {
		membreService.startConversation();
		if (membre == null)
			membre = ModelInitializer.initMembre();
		LOG.info("MemberBean a été initialiser");
		allMembers = membreService.findAll();
		groupes = groupeService.findAll(Groupe.class);
	}

	// this function is execute after the user has setted a field and press the
	// enter button
	// public String validatePassword(ComponentSystemEvent event) {
	// FacesContext fc = FacesContext.getCurrentInstance();
	// UIComponent components = event.getComponent();
	// // get password
	// UIInput uiInputPassword = (UIInput)
	// components.findComponent("motDePass");
	// String password = uiInputPassword.getLocalValue() == null ? "" :
	// uiInputPassword.getLocalValue().toString();
	// String passwordId = uiInputPassword.getClientId();
	//
	// // get confirm password
	// UIInput uiInputConfirmPassword = (UIInput)
	// components.findComponent("confirmerMotDepass");
	// String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ?
	// ""
	// : uiInputConfirmPassword.getLocalValue().toString();
	//
	// // Let required="true" do its job.
	// if (password.isEmpty() || confirmPassword.isEmpty()) {
	// return Pages.SELF;
	// }
	//
	// if (!password.equals(confirmPassword)) {
	// Helper.showError("le mot de passe doit correspondre à sa confirmation",
	// "confirmerMotDepassOut");
	// return Pages.SELF;
	// }
	// return Pages.SELF;
	// }

	public List<Groupe> getGroupes() {
		return groupes;
	}

	public String getMotDePassConfirm() {
		return motDePassConfirm;
	}

	public void setMotDePassConfirm(String motDePassConfirm) {
		this.motDePassConfirm = motDePassConfirm;
	}

	public String close() {
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
	
	public void creerUnMembre(){
		membre = ModelInitializer.initMembre();
		RequestContext.getCurrentInstance().execute("$('#js_creerMembreModal').modal('show');");
	}

	public String save() {
		if (!motDePassConfirm.equals(membre.getMotDePass())) {
			Helper.showError("le mot de passe doit correspondre à sa confirmation", "reste");
			return Pages.SELF;
		}
		membre.setActiver(true);
		Membre m = membreService.createMembre(membre);
		if (m != null) {
			LOG.debug("Le membre " + m.getNom() + " " + m.getPrenom() + " a été creer");
		}
		return Pages.MEMBRES;
	}

	public String delete(Long id) {
		membreService.setMembreActiv(id, false);
		return Pages.MEMBRES;
	}
}
