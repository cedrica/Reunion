package com.reunion.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reunion.business.MembreService;
import com.reunion.business.RondeService;
import com.reunion.common.Pages;
import com.reunion.model.Membre;
import com.reunion.model.Ronde;
import com.reunion.util.CalendarUtils;

@Named
@ConversationScoped
public class RondeBean extends AbstractModalBean<Ronde> implements Serializable {

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
	private boolean rondeCreable;
	
	public void init() {
		rondeService.startConversation();
		rondes = rondeService.findAll();
		LOG.info("RondeBean a été initialiser" + rondes.size() + " rondes ont été télécharger");
		listeDesMembres = membreService.findAll();
		if (membresDelaNouvelleRonde == null)
			membresDelaNouvelleRonde = new ArrayList<>();
		ronde = new Ronde();
	}

	public Ronde getRonde() {
		return ronde;
	}

	public void setRonde(Ronde ronde) {
		this.ronde = ronde;
	}


	public boolean getRondeCreable() {
		return rondeCreable;
	}

	public String setRondeCreable(boolean rondeCreable) {
		this.rondeCreable = rondeCreable;
		return Pages.RONDES;
	}

	public String enlever(Membre membre) {
		membresDelaNouvelleRonde.remove(membre);
		membre.setInserable(false);
		return Pages.RONDES;
	}

	public String ajouter(Membre membre) {
		if (!membresDelaNouvelleRonde.contains(membre)) {
			membresDelaNouvelleRonde.add(membre);
			membre.setInserable(true);
		}
		return Pages.RONDES;
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
	
	public String convertToMonthYear (Date dt){
		if(dt != null){
			return CalendarUtils.getMonthYear(dt);
		}
		return null;
	}

	public String sauvegarderLaRondeCreer() {
//		if (ronde.getDebutDeLaRonde().after(ronde.getFinDeLaRonde())) {
//			Helper.showError("La fin de la ronde doit venir après son début", "compareDate");
//			return Pages.SELF;
//		}
//		int nombreDeparticipants = membresDelaNouvelleRonde.size();
//		long ecartDeMois = CalendarUtils.monthsDifference(CalendarUtils.DateToLocalDate(ronde.getDebutDeLaRonde()),
//				CalendarUtils.DateToLocalDate(ronde.getFinDeLaRonde()));
//		if (nombreDeparticipants != ecartDeMois) {
//			Helper.showError("Le nombre de participants doit correspondre au nombre de tour de la ronde",
//					"compareDate");
//			return Pages.SELF;
//		}
		setRondeCreable(false);
		ronde.setMembres((new HashSet<>(membresDelaNouvelleRonde)));
		rondeService.createRonde(ronde);
		return Pages.RONDES;
	}

	public String getResultat() {
		String res = "";
		for (Membre membre : membresDelaNouvelleRonde) {
			res += membre.getNom() + " " + membre.getPrenom() + "\n";
		}
		return res;
	}

}
