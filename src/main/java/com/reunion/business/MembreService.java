package com.reunion.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;

import com.reunion.dao.GenericDAO;
import com.reunion.model.Membre;

@Stateful
public class MembreService extends GenericDAO<Membre> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public Membre createMembre(Membre entity) {
		return super.create(entity);
	}

	public void update(Long id) {
		update(id, Membre.class);
	}

	public void delete(long id) {
		delete(id, Membre.class);
	}

	public Membre findById(Long id) {
		return findById(id, Membre.class);
	}


	public List<Membre> findAll() {
		return findAll(Membre.class);
	}


}
