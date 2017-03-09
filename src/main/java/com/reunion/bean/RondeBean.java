package com.reunion.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.enterprise.context.ConversationScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reunion.business.MembreService;
import com.reunion.business.RondeService;
import com.reunion.common.Pages;
import com.reunion.model.Membre;
import com.reunion.model.Ronde;

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
	private boolean inserer;
	private Ronde ronde;
	private Membre membreSelecter;
	private String search;
	
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

	public boolean getInserer() {
		return inserer;
	}

	public void setInserer(boolean inserer) {
		if (!inserer) {
			membresDelaNouvelleRonde.remove(membreSelecter);
		}
		this.inserer = inserer;
	}

	public void ajouter(Membre membre) {
		membreSelecter = membre;
		if (!membresDelaNouvelleRonde.contains(membre)){
			membresDelaNouvelleRonde.add(membre);
			membre.setInserable(true);
		}
			
	}

	public void filtre(){
		Membre m = listeDesMembres.stream().filter(new Predicate<Membre>() {
			@Override
			public boolean test(Membre t) {
				return t.getNom().startsWith(search);
			}
		}).collect(Collectors.toList()).get(0);
		listeDesMembres.remove(m);
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

	public String creerUneRonde() {
		setRondeCreable(false);
		ronde.setMembres((new HashSet<>(membresDelaNouvelleRonde)));
		rondeService.create(ronde);
		return Pages.SELF;
	}
	
	public String getResultat(){
		String res = "";
		for (Membre membre : membresDelaNouvelleRonde) {
			res += membre.getNom()+" "+membre.getPrenom()+"\n";
		}
		return res;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	
}
