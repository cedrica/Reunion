package com.reunion.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reunion.business.MembreService;
import com.reunion.business.RondeService;
import com.reunion.common.Helper;
import com.reunion.common.Pages;
import com.reunion.model.Membre;
import com.reunion.model.Ronde;
import com.reunion.util.CalendarUtils;

@Named
@ConversationScoped
public class RondeBean implements Serializable {

	@Inject
	private RondeService rondeService;
	@Inject
	MembreService membreService;
	private List<Ronde> rondes;
	private static final long serialVersionUID = 1L;
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	protected List<Membre> listeDesMembres;
	private List<Membre> membresDelaNouvelleRonde;
	private Ronde ronde;
	private Membre membreSelectionner;
	private boolean isEditModus;

	public void init() {
		rondeService.startConversation();
		rondes = rondeService.findAll();
		LOG.info("RondeBean a été initialiser" + rondes.size() + " rondes ont été télécharger");
		listeDesMembres = membreService.findAll();
		if (membresDelaNouvelleRonde == null)
			membresDelaNouvelleRonde = new ArrayList<>();
		ronde = new Ronde();
	}

	public Membre getMembreSelectionner() {
		return membreSelectionner;
	}

	public void setMembreSelectionner(Membre membreSelectionner) {
		this.membreSelectionner = membreSelectionner;
	}

	public Ronde getRonde() {
		return ronde;
	}

	public String actualiserLeMembre(Ronde ronde) {
		membreSelectionner.setEditable(false);
		ronde.getMembres().add(membreSelectionner);// merge automatic because
													// many to many relationship
		rondeService.createRonde(ronde);
		isEditModus = false;
		return Pages.RONDES;
	}

	public String setEditable(Membre membre, boolean b) {
		setMembreSelectionner(membre);
		membreSelectionner.setEditable(b);
		if (isEditModus) {
			RequestContext.getCurrentInstance().execute(
					"alert('Vous ne pouvez éditer deux objects en même temps. veuillez finir avec le premier avant d´attaquer le second');");
			return Pages.SELF;
		}
		isEditModus = true;
		return Pages.RONDES;
	}

	public void setRonde(Ronde ronde) {
		this.ronde = ronde;
	}

	public void setMembreInserable(Membre membre) {
		membre.setInserable(true);
	}

	public void creerUneRonde() {
		ronde = new Ronde();
		ronde.setMembres(new HashSet<Membre>());
		membresDelaNouvelleRonde.clear();
		RequestContext.getCurrentInstance().execute("$('#js_creerUneRondeModal').modal('show');");
	}

	public void enlever(Membre membre) {
		membresDelaNouvelleRonde.remove(membre);
		membre.setInserable(false);
	}

	public void ajouter() {
		for (Membre membre : listeDesMembres) {
			if (membre.getInserable()) {
				if (!membresDelaNouvelleRonde.contains(membre))
					membresDelaNouvelleRonde.add(membre);
			} else {
				membresDelaNouvelleRonde.remove(membre);
			}

		}
	}

	public String enleverDelaRonde(Ronde ronde, Membre membre) {

		for (Ronde r : rondes) {
			if (r.getId() == ronde.getId()) {
				r.getMembres().remove(membre);
				if (r.getMembres().size() == 0) {
					rondeService.delete(r.getId());
				}
				break;
			}
		}
		return Pages.RONDES;
	}

	public void ajouter(Membre membre) {
		if (!membresDelaNouvelleRonde.contains(membre)) {
			membresDelaNouvelleRonde.add(membre);
		}
	}

	public List<Membre> getMembresDelaNouvelleRonde() {
		return membresDelaNouvelleRonde;
	}

	public void setMembresDelaNouvelleRonde(List<Membre> membresDelaNouvelleRonde) {
		this.membresDelaNouvelleRonde = membresDelaNouvelleRonde;
	}

	public List<Membre> getListeDesMembres() {
		return listeDesMembres;
	}

	public void setListeDesMembres(List<Membre> listeDesMembres) {
		this.listeDesMembres = listeDesMembres;
	}

	public List<Ronde> getRondes() {
		return rondes;
	}

	public void setRondes(List<Ronde> rondes) {
		this.rondes = rondes;
	}

	public boolean sauvegardable() {

		return false;
	}

	public String convertToMonthYear(Date dt) {
		if (dt != null) {
			return CalendarUtils.getMonthYear(dt);
		}
		return null;
	}

	public String sauvegarderLaRondeCreer() {
		LocalDate debut = CalendarUtils.DateToLocalDate(ronde.getDebutDeLaRonde());
		LocalDate premierDuMoi = debut.with(TemporalAdjusters.firstDayOfMonth());
		ronde.setDebutDeLaRonde(CalendarUtils.localDateToDate(premierDuMoi));
		LocalDate fin = premierDuMoi.plusMonths(membresDelaNouvelleRonde.size() - 1);
		ronde.setFinDeLaRonde(CalendarUtils.localDateToDate(fin));
		if (membresDelaNouvelleRonde.isEmpty()) {
			Helper.showError("Aucun membre n´a été ajouté", "compareDate");
			return Pages.SELF;
		}

		if (!Helper.coherance(membresDelaNouvelleRonde, listeDesRangs())) {
			Helper.showError("L´attribution des rangs de bouffe aux membres n´est pas coherante. Veuillez recommencer",
					"compareDate");
			membresDelaNouvelleRonde.clear();
			return Pages.SELF;
		}
		membreService.startConversation();
		for (Membre membre : membresDelaNouvelleRonde) {
			membre.getTrafique().setDateDeBouffe(
					CalendarUtils.localDateToDate(premierDuMoi.plusMonths(membre.getTrafique().getRang() - 1)));
			membreService.createMembre(membre);// update

		}
		ronde.setMembres((new HashSet<>(membresDelaNouvelleRonde)));
		rondeService.createRonde(ronde);
		return Pages.RONDES;
	}

	public List<Integer> listeDesRangs() {
		List<Integer> result = new ArrayList<>();
		for (int j = 1; j <= listeDesMembres.size(); j++) {
			result.add(j);
		}
		return result;
	}

}
