package com.reunion.business;

import java.io.Serializable;
import java.util.ArrayList;
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
		this.conversation.end();
		return super.create(entity);
	}

	public void update(Long id) {
		this.conversation.end();
		update(id, Ronde.class);
	}

	public void delete(long id) {
		this.conversation.end();
		delete(id, Ronde.class);
	}

	public Ronde findById(Long id) {
		this.conversation.end();
		return findById(id, Ronde.class);
	}

	public List<Ronde> findAll() {
		List<Ronde> rondes = findAll(Ronde.class);
		List<Ronde> result = new ArrayList<>();
		return rondes;
	}
}