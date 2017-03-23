package com.reunion.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reunion.business.EmpruntService;
import com.reunion.business.MembreService;
import com.reunion.business.RondeService;
import com.reunion.common.Pages;
import com.reunion.enums.StatusDeRemboursement;
import com.reunion.helper.ModelInitializer;
import com.reunion.model.Emprunt;
import com.reunion.model.Membre;
import com.reunion.model.Ronde;
import com.reunion.util.CalendarUtils;

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
		emprunts = empruntService.toutLesEmprunts();
		for (Emprunt emprunt : emprunts) {
			if(CalendarUtils.date1BeforeDate2(new Date(), emprunt.getDateDeRemboursement(),true)){
				emprunt.setStatus(StatusDeRemboursement.DATE_DEPASSEE);
			}
		}
		if (rondes == null) {
			rondes = rondeService.findAll();
		}

		// for editing case
		if (emprunt == null) {
			emprunt = ModelInitializer.initEmprunt();
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

	public String goToEmprunt() {
		return Pages.EMPRUNT;
	}

	public void creerUnEmprunt() {
		emprunt = ModelInitializer.initEmprunt();
		emprunt.setStatus(StatusDeRemboursement.OUVERT);
	}

	public String rembourser(boolean activer) {
		StatusDeRemboursement statusDeRemboursement = (activer) ? StatusDeRemboursement.REMBOURSE
				: StatusDeRemboursement.OUVERT;
		Date dateDeRemboursement = (activer)? new Date():emprunt.getDateDeRemboursement();
		this.emprunt.setDateDeRemboursement(dateDeRemboursement);
		this.emprunt.setStatus(statusDeRemboursement);
		sauve();
		return Pages.EMPRUNT;
	}

	public Float calculDuBenefice() {
		return null;
	}

	public String empruntEditable(boolean editable) {
		this.emprunt.setEditable(editable);
		return Pages.EMPRUNT;
	}

	public Float calculDuFondDeCaisse() {
		Float fondDeCaisse = 0f;
		for (Membre membre : membres) {
			if (membre.getTrafique() != null) {
				if (membre.getTrafique().getFondDeCaisse() != null) {
					fondDeCaisse += membre.getTrafique().getFondDeCaisse();
				}
			}
		}
		return fondDeCaisse;
	}

	public Float calculDuRemboursement(Emprunt emprunt) {
		return (emprunt != null) ? (emprunt.getSommeEmpruntee() + emprunt.getSommeEmpruntee() * 0.02f) : null;
	}

	public String suprimerLeRemboursement(Emprunt emprunt){
		empruntService.delete(emprunt.getId());
		return Pages.EMPRUNT;
	}

	public String sauvegarder() {
		emprunt.setEditable(false); //toujours
		if(emprunt.getStatus() == StatusDeRemboursement.OUVERT){
			emprunt.setDateDeLemprunt(new Date());// aujourdhui
		}
		sauve();
		return Pages.EMPRUNT;
	}

	private void sauve() {
		Emprunt res = empruntService.create(emprunt);
		if (res != null) {
			LOG.info("l´Eprunt " + res.toString() + " a été creer");
		}
	}
}
