package com.reunion.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;

import com.reunion.common.Pages;
import com.reunion.dao.GenericDAO;
import com.reunion.model.Emprunt;

@Stateful
public class EmpruntService extends GenericDAO<Emprunt> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	Conversation conversation;

	public String createEmprunt(Emprunt entity) {
		conversation.end();
		super.create(entity);
		return Pages.EMPRUNT;
	}

	public String update(Long id) {
		conversation.end();
		update(id, Emprunt.class);
		return Pages.EMPRUNT;
	}

	public String delete(long id) {
		conversation.end();
		delete(id, Emprunt.class);
		return Pages.EMPRUNT;
	}

	public Emprunt findById(Long id) {
		conversation.end();
		return findById(id, Emprunt.class);
	}

	public void startConversation() {
		if (this.conversation.isTransient()) {
			this.conversation.begin();
		}
	}

	public List<Emprunt> toutLesEmprunts() {
		return findAll(Emprunt.class);
	}

}
