package com.reunion.dao;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import com.reunion.model.Membre;

@Stateful
public class StaticDAO {
	@PersistenceContext(unitName = "Reunion-persistence-unit", type = PersistenceContextType.EXTENDED)
	private  EntityManager entityManager;

	public <T> T findById(Long id, Class<T> clazz) {
		return entityManager.find(clazz, id);
	}

	public  Membre findMembreByNomPrenom(String nom, String prenom) {
		TypedQuery<Membre> query = entityManager.createQuery("SELECT * FROM Membre m where m.nom like '%"+nom+"%' and m.prenom like '%"+prenom+"%'", Membre.class);
		List<Membre> results = query.getResultList();
		return (results != null && !results.isEmpty())? results.get(0):null;
	}
}
