package com.reunion.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.reunion.dao.GenericDAO;
import com.reunion.model.Trafique;

@Stateful
public class TrafiqueService extends GenericDAO<Trafique> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	public Trafique createTrafique(Trafique entity) {
		return super.create(entity);
	}

	public void update(Long id) {
		this.conversation.end();
		update(id, Trafique.class);
	}

	public void delete(long id) {
		this.conversation.end();
		delete(id, Trafique.class);
	}

	public Trafique findById(Long id) {
		this.conversation.end();
		return findById(id, Trafique.class);
	}

	public List<Trafique> findAll() {
		List<Trafique> trafiques = findAll(Trafique.class);
		return trafiques;
	}

	public void endConversation() {
		this.conversation.end();
	}

}
