package com.reunion.business;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.reunion.dao.GenericDAO;
import com.reunion.model.Membre;

@Stateful
public class MembreService extends GenericDAO<Membre> implements Serializable {
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

	public Membre createMembre(Membre entity) {
		this.conversation.end();
		return super.create(entity);
	}

	public void update(Long id) {
		this.conversation.end();
		update(id, Membre.class);
	}

	public void delete(long id) {
		this.conversation.end();
		delete(id, Membre.class);
	}

	public void setMembreActiv(long id, boolean activation) {
		this.conversation.end();
		String sql = "UPDATE Membre set activer =:activer where id =:id ";
		executeCustomQuery(sql , Membre.class, activation,id);
	}
	
	public Membre findById(Long id) {
		this.conversation.end();
		return findById(id, Membre.class);
	}

	public List<Membre> findAll() {
		List<Membre> membres = findAll(Membre.class);
		membres = membres.stream().filter(new Predicate<Membre>() {
			@Override
			public boolean test(Membre t) {
				return t.getActiver();//juste les membres actif
			}
		}).collect(Collectors.toList());
		return membres;
	}

}
