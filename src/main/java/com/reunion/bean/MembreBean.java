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
public class MembreBean extends AbstractModalBean<Membre> implements Serializable {

	@Inject
	MembreService membreService;

	private List<Membre> allMembers;
	private Membre membre;
	private static final long serialVersionUID = 1L;
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	

	public void init() {
		membreService.startConversation();
		if (membre == null)
			membre = ModelInitializer.initMembre();
		LOG.info("MemberBean a été initialiser");
		if(allMembers == null){
			allMembers = membreService.findAll();
		}
	}

	public String save() {
		Membre m = membreService.createMembre(membre);
		if (m != null) {
			LOG.debug("Le membre " + m.getNom() + " a été creer");
		}
		return Pages.MEMBRES;
	}

	public String close() {
		setShowModal(false);
		return Pages.MEMBRES;
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}

	public List<Membre> getAllMembers() {
		return allMembers;
	}

	public void setAllMembers(List<Membre> allMembers) {
		this.allMembers = allMembers;
	}

	public String delete(Long id){
		membreService.delete(id);
		return Pages.MEMBRES;
	}
}
