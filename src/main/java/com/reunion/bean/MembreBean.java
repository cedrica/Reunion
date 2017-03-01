package com.reunion.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reunion.business.MembreService;
import com.reunion.common.Helper;
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
	private boolean inputsValid;
	private HashMap<String, String> map = null;

	public void init() {
		if (map == null) {
			map = new HashMap<>();
			map.put("email", "");
			map.put("nom", "");
			map.put("prenom", "");
		}
		membreService.startConversation();
		membre = ModelInitializer.initMembre();
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}

	public void entreLeNom(AjaxBehaviorEvent e) {
		String id = e.getComponent().getId();
		String valeur = (String) ((UIOutput) e.getSource()).getValue();
		map.put(id, valeur);
		if (!map.get("nom").isEmpty() && !map.get("prenom").isEmpty() && !map.get("email").isEmpty()) {
			setInputsValid(true);
		}
	}

	public String save() {
		Membre m = membreService.createMembre(membre);
		if (m != null) {
			LOG.debug("Le membre " + m.getNom() + " a été creer");
		}
		return Pages.MEMBRES;
	}

	public boolean isInputsValid() {
		return this.inputsValid;
	}

	public void setInputsValid(boolean inputsValid) {
		System.out.println("inputs Valid :" + inputsValid);
		this.inputsValid = inputsValid;
	}

	public List<Membre> getAllMembers() {
		allMembers = membreService.findAll();
		return allMembers;
	}

	public void setAllMembers(List<Membre> allMembers) {
		this.allMembers = allMembers;
	}

}
