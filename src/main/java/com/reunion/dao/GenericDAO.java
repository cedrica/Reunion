package com.reunion.dao;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericDAO<T> {
	@PersistenceContext(unitName = "Reunion-persistence-unit", type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	private static Logger LOG = LoggerFactory.getLogger(GenericDAO.class);


	public T create(T entity) {
		
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
