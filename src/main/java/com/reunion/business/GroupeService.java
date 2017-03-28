package com.reunion.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.reunion.common.Pages;
import com.reunion.dao.GenericDAO;
import com.reunion.model.Groupe;

@Stateful
public class GroupeService extends GenericDAO<Groupe> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	Conversation conversation;

	public Groupe createGroupe(Groupe entity) {
		return super.create(entity);
	}

	public String update(Long id) {
		update(id, Groupe.class);
		return Pages.GROUPES;
	}

	public String delete(long id) {
		delete(id, Groupe.class);
		return Pages.GROUPES;
	}

	public Groupe findById(Long id) {
		return findById(id, Groupe.class);
	}

	public void startConversation() {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		if (this.conversation.isTransient()) {
			this.conversation.begin();
			this.conversation.setTimeout(1800000L);
		}
	}

	public List<Groupe> toutLesGroupes() {
		return findAll(Groupe.class);
	}

	public void endConversation() {
		conversation.end();
	}

}
