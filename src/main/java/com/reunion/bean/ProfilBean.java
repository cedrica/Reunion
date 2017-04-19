package com.reunion.bean;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.reunion.business.GroupeService;
import com.reunion.business.MembreService;
import com.reunion.common.Helper;
import com.reunion.common.Pages;
import com.reunion.model.Groupe;
import com.reunion.model.Membre;
import com.reunion.util.SessionUtil;

@Named
@ConversationScoped
public class ProfilBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StreamedContent monImage;
	private String motDePassConfirm;
	private Membre membreActuel;
	private boolean profilEditable;
	@Inject
	private MembreService membreService;
	@Inject
	private GroupeService groupeService;

	public void init() {
		HttpSession session = SessionUtil.getSession();
		membreActuel = (Membre) session.getAttribute(SessionUtil.MEMBRE_ACTUEL);
		this.profilEditable = membreActuel.getEditable();
	}

	public void onCapture() {
		String data = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("data");
		if (data != null && data.length() > 1 && data.startsWith("data:image/")) {
			String monImageStr = data.split(",")[1];
			byte[] imageBytes = DatatypeConverter.parseBase64Binary(monImageStr);
			if(membreActuel == null){
				HttpSession session = SessionUtil.getSession();
				membreActuel = (Membre) session.getAttribute(SessionUtil.MEMBRE_ACTUEL);
			}
			this.membreActuel.setMonImage(imageBytes);
			membreService.createMembre(this.membreActuel);
		}
	}


	public boolean getProfilEditable() {
		return profilEditable;
	}

	public String setProfilEditable(boolean profilEditable) {
		if (profilEditable) {
			return Pages.PROFILE_EDITOR;
		} else {
			return Pages.PROFILE;
		}
	}

	public Membre getMembreActuel() {
		HttpSession session = SessionUtil.getSession();
		membreActuel = (Membre) session.getAttribute(SessionUtil.MEMBRE_ACTUEL);
		return membreActuel;
	}

	public void setMembreActuel(Membre membreActuel) {
		this.membreActuel = membreActuel;
	}

	public String getMotDePassConfirm() {
		return motDePassConfirm;
	}

	public void setMotDePassConfirm(String motDePassConfirm) {
		this.motDePassConfirm = motDePassConfirm;
	}

	public StreamedContent getMonImage() {
		if (membreActuel.getMonImage() != null)
			monImage = new DefaultStreamedContent(new ByteArrayInputStream(membreActuel.getMonImage()), "image/png");
		return this.monImage;
	}

	public void setMonImage(StreamedContent monImage) {
		this.monImage = monImage;
	}

	public String sauvegarderProfil() {
		if (!motDePassConfirm.equals(membreActuel.getMotDePass())) {
			Helper.showError("le mot de passe doit correspondre à sa confirmation", "profilError");
			return Pages.SELF;
		}
		membreService.create(membreActuel);

		HttpSession session = SessionUtil.getSession();
		session.setAttribute(SessionUtil.MEMBRE_ACTUEL, membreActuel);
		return Pages.PROFILE;
	}

	public List<Groupe> tousLesGroupes() {
		return groupeService.findAll(Groupe.class);
	}

	public String reload() {
		return Pages.PROFILE;
	}

}
