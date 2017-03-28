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

import com.reunion.business.GroupeService;
import com.reunion.business.MembreService;
import com.reunion.business.RondeService;
import com.reunion.business.TrafiqueService;
import com.reunion.common.Helper;
import com.reunion.common.Pages;
import com.reunion.model.Groupe;
import com.reunion.model.Membre;
import com.reunion.model.Ronde;
import com.reunion.model.Trafique;
import com.reunion.util.CalendarUtils;

@Named
@ConversationScoped
public class RondeBean implements Serializable {

	@Inject
	private RondeService rondeService;
	@Inject
	private GroupeService groupeService;
	@Inject
	private MembreService membreService;
	@Inject
	private TrafiqueService trafiqueService;
	private List<Ronde> rondes;
	private List<Groupe> groupes;
	private static final long serialVersionUID = 1L;
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	private List<Trafique> trafiquesDelaNouvelleRonde;

	private Ronde ronde;
	private Groupe groupe;
	private boolean isEditModus;
	private Trafique trafique;
	private List<Trafique> listeDesTrafiques;

	private List<Membre> listeDesMembres;

	public void init() {
		rondeService.startConversation();
		trafiqueService.startConversation();
		rondes = rondeService.findAll();
		LOG.info("RondeBean a été initialiser" + rondes.size() + " rondes ont été télécharger");
		listeDesMembres = membreService.findAll();
		if (trafiquesDelaNouvelleRonde == null)
			trafiquesDelaNouvelleRonde = new ArrayList<>();
		ronde = new Ronde();
		groupes = groupeService.findAll(Groupe.class);

	}

	public List<Trafique> getTrafiquesDelaNouvelleRonde() {
		return trafiquesDelaNouvelleRonde;
	}

	public List<Trafique> getListeDesTrafiques() {
		return listeDesTrafiques;
	}

	public Trafique getTrafique() {
		return trafique;
	}

	public void setTrafique(Trafique trafique) {
		this.trafique = trafique;
	}

	public List<Groupe> getGroupes() {
		return groupes;
	}

	public Groupe getGroupe() {

		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		listeDesTrafiques = new ArrayList<Trafique>();
		List<Membre> membres = Helper.trouveLesMembreDuGroupe(listeDesMembres, groupe);
		for (Membre m : membres) {
			trafique = new Trafique();
			trafique.setMembre(m);
			listeDesTrafiques.add(trafique);
		}
		this.groupe = groupe;
	}

	public Ronde getRonde() {
		return ronde;
	}

	public void setRonde(Ronde ronde) {
		this.ronde = ronde;
	}

	public void setMembreInserable(Membre membre) {
		membre.setInserable(true);
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

	public boolean appartientARonde(Ronde ronde) {
		return (this.ronde != null) ? ronde.getTrafiques().contains(this.trafique) : false;
	}

	public float calculDeLaRistourne(Ronde ronde, Trafique trafique) {
		if (trafique == null || trafique.getCotisation() == null || ronde == null)
			return 0;
		List<Trafique> trafiquesDeLaRonde = new ArrayList<>(ronde.getTrafiques());
		double somme = trafiquesDeLaRonde.stream().filter(t -> t != null && t.getCotisation() != null)
				.mapToDouble(t -> t.getCotisation()).sum();

		if (trafiquesDeLaRonde != null && !trafiquesDeLaRonde.isEmpty()) {
			for (Trafique t : trafiquesDeLaRonde) {
				if (t.getId() == trafique.getId())
					continue;
				if (t.getCotisation() != null && (t.getCotisation() > trafique.getCotisation())) {
					somme -= (t.getCotisation() - trafique.getCotisation());
				}
			}
		}
		return (float) somme;
	}

	public float calculDuSupplement(Ronde ronde, Trafique trafique) {
		if (trafique == null || trafique.getCotisation() == null || ronde == null)
			return 0;
		List<Trafique> trafiquesDeLaRonde = new ArrayList<>(ronde.getTrafiques());
		double supplement = 0;

		if (trafiquesDeLaRonde != null && !trafiquesDeLaRonde.isEmpty()) {
			for (Trafique t : trafiquesDeLaRonde) {
				if (t.getId() == trafique.getId())
					continue;
				if (t.getCotisation() != null && (trafique.getCotisation() > t.getCotisation())) {
					supplement += (trafique.getCotisation()-t.getCotisation());
				}
			}
		}
		return (float) supplement;
	}

	public String sauvegarderLaRondeCreer() {
		LocalDate debut = CalendarUtils.DateToLocalDate(ronde.getDebutDeLaRonde());
		LocalDate premierDuMoi = debut.with(TemporalAdjusters.firstDayOfMonth());
		ronde.setDebutDeLaRonde(CalendarUtils.localDateToDate(premierDuMoi));
		LocalDate fin = premierDuMoi.plusMonths(trafiquesDelaNouvelleRonde.size() - 1);
		ronde.setFinDeLaRonde(CalendarUtils.localDateToDate(fin));
		if (trafiquesDelaNouvelleRonde.isEmpty()) {
			Helper.showError("Aucun membre n´a été ajouté", "compareDate");
			return Pages.SELF;
		}

		if (!Helper.coherance(trafiquesDelaNouvelleRonde, listeDesRangs())) {
			Helper.showError("L´attribution des rangs de bouffe aux membres n´est pas coherante. Veuillez recommencer",
					"compareDate");
			trafiquesDelaNouvelleRonde.clear();
			return Pages.SELF;
		}
		for (Trafique trafique : trafiquesDelaNouvelleRonde) {
			trafique.setDateDeBouffe(CalendarUtils.localDateToDate(premierDuMoi.plusMonths(trafique.getRang() - 1)));
		}

		for (Trafique trafique : trafiquesDelaNouvelleRonde) {
			trafique = trafiqueService.createTrafique(trafique);
			ronde.getTrafiques().add(trafique);
		}
		trafiqueService.endConversation();
		rondeService.createRonde(ronde);
		return Pages.RONDES;
	}

	public void creerUneRonde() {
		ronde = new Ronde();
		trafique = new Trafique();
		ronde.setTrafiques(new HashSet<Trafique>());
		trafiquesDelaNouvelleRonde.clear();
		RequestContext.getCurrentInstance().execute("$('#js_creerUneRondeModal').modal('show');");
	}

	public List<Integer> listeDesRangs() {
		List<Integer> result = new ArrayList<>();
		for (int j = 1; j <= listeDesTrafiques.size(); j++) {
			result.add(j);
		}
		return result;
	}

	public String actualiserLeTrafique(Trafique trafique) {
		trafique.setEditable(false);
		trafique = trafiqueService.create(trafique);
		trafiqueService.endConversation();
		isEditModus = false;
		return Pages.RONDES;
	}

	public String setEditable(Ronde ronde, Trafique trafique) {
		setRonde(ronde);
		setTrafique(trafique);
		this.trafique.setEditable(true);
		if (isEditModus) {
			RequestContext.getCurrentInstance().execute(
					"alert('Vous ne pouvez éditer deux objects en même temps. veuillez finir avec le premier avant d´attaquer le second');");
			return Pages.SELF;
		}
		isEditModus = true;
		return Pages.RONDES;
	}

	public void enleverTrafique(Trafique trafique) {
		trafiquesDelaNouvelleRonde.remove(trafique);
	}

	public String enleverDelaRonde(Ronde ronde, Trafique trafique) {
		for (Ronde r : rondes) {
			if (r.getId() == ronde.getId()) {
				r.getTrafiques().remove(trafique);
				if (r.getTrafiques().size() == 0) {
					rondeService.delete(r.getId());
				} else {
					rondeService.create(r);
				}
				break;
			}
		}
		return Pages.RONDES;
	}

	public void ajouterTrafique(Trafique trafique) {
		if (trafiquesDelaNouvelleRonde.size() < 1) {
			trafiquesDelaNouvelleRonde.add(trafique);
			return;
		}
		boolean present = false;
		for (Trafique t : trafiquesDelaNouvelleRonde) {
			if (t.getMembre().getId() == trafique.getMembre().getId()) {
				present = true;
			}
		}
		if (!present)
			trafiquesDelaNouvelleRonde.add(trafique);
	}
}
