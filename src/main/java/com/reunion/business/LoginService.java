package com.reunion.business;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reunion.dao.GenericDAO;
import com.reunion.model.Membre;

@Stateful
public class LoginService extends GenericDAO<Membre> {
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
		if (!this.conversation.isTransient())
			this.conversation.end();
	}

	public Membre findMembreByEmail(String email) {

		try {
			List<Membre> membres = findAll(Membre.class);
			if (membres != null && membres.size() > 0) {
				Membre membre = renvoieLeMembreAyantPourEmail(email, membres);
				LOG.debug("Le membre " + membre.getNom() + " a été creer");
				return membre;
			}

		} catch (Exception e) {
			LOG.error("Il n´existe encore aucun membre avec cette l´e-mail: " + email);
		}

		return null;
	}

	private Membre renvoieLeMembreAyantPourEmail(String email, List<Membre> membres) {
		membres = membres.stream().filter(new Predicate<Membre>() {
			@Override
			public boolean test(Membre t) {
				if (t.getContact() != null && t.getContact().getEmail() != null) {
					return t.getContact().getEmail().equals(email);
				}
				return false;
			}
		}).collect(Collectors.toList());
		System.out.println(membres);
		return (membres.size() > 0) ? membres.get(0) : null;
	}
}
