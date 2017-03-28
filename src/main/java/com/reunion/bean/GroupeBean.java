package com.reunion.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.reunion.business.GroupeService;
import com.reunion.common.Pages;
import com.reunion.model.Groupe;

@Named("groupeBean")
@ConversationScoped
public class GroupeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private GroupeService groupeService;
	private Groupe groupe;
	private List<Groupe> tousLesGroupes;

	public void init() {
		groupeService.startConversation();
		groupe = new Groupe();
		tousLesGroupes = groupeService.toutLesGroupes();
	}

	public void creerUnGroupe() {
		groupe = new Groupe();
		RequestContext.getCurrentInstance().execute("$('#js_creerUnGroupeModal').modal('show');");
	}

	public List<Groupe> getTousLesGroupes() {
		return tousLesGroupes;
	}

	public void setTousLesGroupes(List<Groupe> tousLesGroupes) {
		this.tousLesGroupes = tousLesGroupes;
	}

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	public String sauvegarder() {
		groupeService.createGroupe(groupe);
		groupeService.endConversation();
		return Pages.GROUPES;
	}

}
