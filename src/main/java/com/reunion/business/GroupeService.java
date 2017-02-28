package com.reunion.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
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

	public String createGroupe(Groupe entity) {
		conversation.end();
		super.create(entity);
		return Pages.GROUPES;
	}

	public String update(Long id) {
		conversation.end();
		update(id, Groupe.class);
		return Pages.GROUPES;
	}

	public String delete(long id) {
		conversation.end();
		delete(id, Groupe.class);
		return Pages.GROUPES;
	}

	public Groupe findById(Long id) {
		conversation.end();
		return findById(id, Groupe.class);
	}

	public void startConversation() {
		if (this.conversation.isTransient()) {
			this.conversation.begin();
		}
	}

	public List<Groupe> toutLesGroupes() {
		return findAll(Groupe.class);
	}

}
