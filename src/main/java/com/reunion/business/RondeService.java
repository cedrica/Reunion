package com.reunion.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.reunion.dao.GenericDAO;
import com.reunion.model.Ronde;

@Stateful
public class RondeService extends GenericDAO<Ronde> implements Serializable {
	@Inject
	private Conversation conversation;

	public void startConversation() {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		if (this.conversation.isTransient()) {
			this.conversation.begin();
			this.conversation.setTimeout(1800000L);
		}
	}

	private static final long serialVersionUID = 1L;

	public Ronde createRonde(Ronde entity) {
		return super.create(entity);
	}

	public void update(Long id) {
		update(id, Ronde.class);
	}

	public void delete(long id) {
		delete(id, Ronde.class);
	}

	public Ronde findById(Long id) {
		return findById(id, Ronde.class);
	}

	public List<Ronde> findAll() {
		List<Ronde> rondes = findAll(Ronde.class);
		return rondes;
	}

	public void endConversation() {
		this.conversation.end();
	}
}