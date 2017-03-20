package com.reunion.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reunion.business.EmpruntService;
import com.reunion.business.MembreService;
import com.reunion.business.RondeService;
import com.reunion.common.Pages;
import com.reunion.enums.StatusDeRemboursement;
import com.reunion.model.Emprunt;
import com.reunion.model.Membre;
import com.reunion.model.Ronde;

@ConversationScoped
@Named
public class EmpruntBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Membre> membres;
	private List<Emprunt> emprunts;
	private List<Ronde> rondes;
	private Emprunt emprunt;
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	private boolean editerOuRembourser;
	private boolean empruntCreer;
	
	@Inject
	private EmpruntService empruntService;
	@Inject
	private MembreService membreService;
	@Inject
	private RondeService rondeService;

	public void init() {
		empruntService.startConversation();
		if (membres == null) {
			membres = membreService.findAll();
		}
		if (emprunts == null || emprunts.isEmpty() || empruntCreer) {
			emprunts = empruntService.toutLesEmprunts();
			;
		}
		if (rondes == null) {
			rondes = rondeService.findAll();
		}
		
		//for editing case
		if(emprunt == null){
			emprunt = new Emprunt();
			emprunt.setMembre(new Membre());
		}
	}

	public List<Ronde> getRondes() {
		return rondes;
	}

	public void setRondes(List<Ronde> rondes) {
		this.rondes = rondes;
	}

	public List<Emprunt> getEmprunts() {
		return emprunts;
	}

	public void setEmprunts(List<Emprunt> emprunts) {
		this.emprunts = emprunts;
	}

	public Emprunt getEmprunt() {
		return emprunt;
	}

	public void setEmprunt(Emprunt emprunt) {
		this.emprunt = emprunt;
	}

	public List<Membre> getMembres() {
		return membres;
	}

	public void setMembres(List<Membre> membres) {
		this.membres = membres;
	}

	
	public boolean getEditerOuRembourser() {
		return editerOuRembourser;
	}

	public void setEditerOuRembourser(boolean editerOuRembourser) {
		this.editerOuRembourser = editerOuRembourser;
	}

	public void creerUnEmprunt() {
		empruntCreer = false;
		setEditerOuRembourser(false);
		emprunt = new Emprunt();
		emprunt.setMembre(new Membre());
	}
	
	public void editerOuRembourser(Emprunt emprunt){
		setEditerOuRembourser(true);
		setEmprunt(emprunt);
		// call modal window vie Request context in order to run javascript after Bean method
		RequestContext.getCurrentInstance().execute("$('#js_creerEmpruntModal').modal('show');");

	}
	
	public Float calculDuBenefice(){
		return null;
	}

	public Float calculDuFondDeCaisse() {
		Float fondDeCaisse = 0f;
		for (Membre membre : membres) {
			if (membre.getTrafique() != null){
				if(membre.getTrafique().getFondDeCaisse() != null){
					fondDeCaisse += membre.getTrafique().getFondDeCaisse();
				}
			}
		}
		return fondDeCaisse;
	}

	public Float calculDuRemboursement(Emprunt emprunt) {
		return (emprunt != null)? (emprunt.getSommeEmpruntee() + emprunt.getSommeEmpruntee()*0.02f):null;
	}

	public String sauvegarder() {
		emprunt.setDateDeLemprunt(new Date());// aujourdhui
		emprunt.setStatus(StatusDeRemboursement.OUVERT);
		Emprunt create = empruntService.create(emprunt);
		if (create != null) {
			LOG.info("l´Eprunt " + create.toString() + " a été creer");
		}
		empruntCreer = true;	
		emprunt = new Emprunt();
		emprunt.setMembre(new Membre());
		return Pages.EMPRUNT;
	}
}
