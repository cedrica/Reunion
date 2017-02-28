package com.reunion.dao;

import java.util.List;

import javax.enterprise.context.Conversation;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericDAO<T> {
	@PersistenceContext(unitName = "Reunion-persistence-unit", type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	@Inject
	private Conversation conversation;
	private static Logger LOG = LoggerFactory.getLogger(GenericDAO.class);
	
	public void startConversation() {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		if (this.conversation.isTransient()) {
			this.conversation.begin();
			this.conversation.setTimeout(1800000L);
		}
	}

	public void stopperLaConversation() {
		this.conversation.end();
	}

	public T create(T entity) {
		this.conversation.end();
		try {
			this.entityManager.persist(entity);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
		return entity;
	}

	public void update(Long id, Class<T> clazz) {
		this.conversation.end();
		T entity = findById(id, clazz);
		try {
			if (id == null) {
				this.entityManager.persist(entity);
			} else {
				this.entityManager.merge(entity);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
	}

	public void delete(long id, Class<T> clazz) {
		this.conversation.end();
		try {
			T deletableEntity = findById(id, clazz);
			this.entityManager.remove(deletableEntity);
			this.entityManager.flush();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
	}

	public T findById(Long id, Class<T> clazz) {
		return this.entityManager.find(clazz, id);
	}

	public List<T> findAll(Class<T> clazz) {
		CriteriaQuery<T> criteria = this.entityManager.getCriteriaBuilder().createQuery(clazz);
		return this.entityManager.createQuery(criteria.select(criteria.from(clazz))).getResultList();
	}

}
