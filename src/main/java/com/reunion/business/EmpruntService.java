package com.reunion.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;

import com.reunion.dao.GenericDAO;
import com.reunion.model.Emprunt;

@Stateful
public class EmpruntService extends GenericDAO<Emprunt> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	Conversation conversation;

	public Emprunt createEmprunt(Emprunt entity) {
		conversation.end();
		return super.create(entity);
	}

	public void update(Long id) {
		if (conversation.isTransient())
			conversation.end();
		update(id, Emprunt.class);
	}

	public void delete(long id) {
		if (conversation.isTransient())
			conversation.end();
		delete(id, Emprunt.class);
	}

	public Emprunt findById(Long id) {
		if (conversation.isTransient())
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
