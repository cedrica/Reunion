package com.reunion.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reunion.business.MembreService;
import com.reunion.common.Pages;
import com.reunion.helper.ModelInitializer;
import com.reunion.model.Membre;

@Named
@ConversationScoped
public class MembreBean extends BasicBean implements Serializable {


	@Inject
	MembreService membreService;
	
	private List<Membre> allMembers;
	private Membre membre;
	private static final long serialVersionUID = 1L;
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	

	
	public void init() {
		membreService.startConversation();
		membre = ModelInitializer.initMembre();
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}

	public String save() {
		Membre m = membreService.createMembre(membre);
		if (m != null) {
			LOG.debug("Le membre " + m.getNom() + " a été creer");
		}
		return Pages.MEMBRES;
	}

	public List<Membre> getAllMembers() {
		allMembers = membreService.findAll();
		return allMembers;
	}

	public void setAllMembers(List<Membre> allMembers) {
		this.allMembers = allMembers;
	}
}
