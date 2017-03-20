package com.reunion.dao;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericDAO<T> {
	@PersistenceContext(unitName = "Reunion-persistence-unit", type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	private static Logger LOG = LoggerFactory.getLogger(GenericDAO.class);

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public T create(T entity) {
		
		try {
			return this.entityManager.merge(entity);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
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
			LOG.info(e.getMessage());
		}
	}

	public T findById(Long id, Class<T> clazz) {
		return this.entityManager.find(clazz, id);
	}

	public boolean executeCustomQuery(String customQuery, Class clazz, Object ...params) {
		Query query = entityManager.createQuery(customQuery);
		query.setParameter("activer", params[0]);
		query.setParameter("id", params[1]);
		try{
			int numberOfUpdatedEntities = query.executeUpdate();
			LOG.info(numberOfUpdatedEntities+" Données ont été modifié dans la table "+clazz.getSimpleName());
			return true;
		}catch (Exception e) {
			LOG.error("Query :"+customQuery+" konnte nicht ausgeführt werden");
			return false;
		}
	}
	
	public List<T> findAll(Class<T> clazz) {
		CriteriaQuery<T> criteria = this.entityManager.getCriteriaBuilder().createQuery(clazz);
		return this.entityManager.createQuery(criteria.select(criteria.from(clazz))).getResultList();
	}

}
