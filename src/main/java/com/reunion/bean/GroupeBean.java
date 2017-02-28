package com.reunion.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.reunion.business.GroupeService;
import com.reunion.model.Groupe;

@Named("groupeBean")
@ConversationScoped
public class GroupeBean extends BasicBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private GroupeService groupeService;
	private Groupe groupe;
	private List<Groupe> tousLesGroupes;

	
	public void init(){
		groupeService.startConversation();
		groupe = new Groupe();
	}
	
	public List<Groupe> getTousLesGroupes() {
		tousLesGroupes = groupeService.toutLesGroupes();
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

	public String creerUnGroupe(){
		return groupeService.createGroupe(groupe);
	}
	

}
