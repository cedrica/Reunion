package com.reunion.bean;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.DatatypeConverter;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
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
	private boolean editMode;

	public void init() {
		membreService.startConversation();
		if (membre == null)
			membre = ModelInitializer.initMembre();
		LOG.info("MemberBean a été initialiser");
		allMembers = membreService.findAll();
		groupes = groupeService.findAll(Groupe.class);
	}

	


	public boolean getEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<Groupe> getGroupes() {
		return groupes;
	}

	public String getMotDePassConfirm() {
		return motDePassConfirm;
	}

	public void setMotDePassConfirm(String motDePassConfirm) {
		this.motDePassConfirm = motDePassConfirm;
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

	public String close() {
		this.membre = null;
		setEditMode(false);
		return Pages.MEMBRES;
	}


	public void ajusterLeFondDeCaisse() {

	}

	public String montreLeMembre(Membre membre) {
		setMembre(membre);
		return Pages.MEMBRE;
	}

	public void creerUnMembre() {
		membre = ModelInitializer.initMembre();
		RequestContext.getCurrentInstance().execute("$('#js_creerMembreModal').modal('show');");
	}

	public void editer(Membre membre) {
		this.membre = membre;
		setEditMode(true);
		RequestContext.getCurrentInstance().execute("$('#js_creerMembreModal').modal('show');");
	}

	public String save() {
		if (!motDePassConfirm.equals(membre.getMotDePass())) {
			Helper.showError("le mot de passe doit correspondre à sa confirmation", "reste");
			return Pages.SELF;
		}
		Membre exite = membreService.findByEmail(membre.getContact().getEmail());
		if (exite != null) {
			Helper.showError("Un membre avec l´email donnée existe deja", "reste");
			return Pages.SELF;
		}
		Membre m = membreService.createMembre(membre);
		if (m != null) {
			LOG.debug("Le membre " + m.getNom() + " " + m.getPrenom() + " a été creer");
		}

		return Pages.MEMBRES;
	}

	public String sauverEdit() {
		if (!motDePassConfirm.equals(membre.getMotDePass())) {
			Helper.showError("le mot de passe doit correspondre à sa confirmation", "reste");
			return Pages.SELF;
		}
		Membre m = membreService.createMembre(membre);
		if (m != null) {
			LOG.debug("Le membre " + m.getNom() + " " + m.getPrenom() + " a été creer");
		}
		this.membre = null;
		setEditMode(false);
		return Pages.MEMBRES;
	}



	public String delete(Long id) {
		if (!membreService.delete(id)) {
			Helper.showError(
					"Le membre que vous voulez supprimer appartient à une ronde. Faudra d´abord l´oter de la ronde avant de procéder à sa suppression",
					"membresErrors");
			return Pages.SELF;
		}
		return Pages.MEMBRES;
	}

}
