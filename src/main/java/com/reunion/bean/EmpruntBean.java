package com.reunion.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reunion.business.EmpruntService;
import com.reunion.business.MembreService;
import com.reunion.business.RondeService;
import com.reunion.common.Helper;
import com.reunion.common.Pages;
import com.reunion.enums.StatusDeRemboursement;
import com.reunion.helper.ModelInitializer;
import com.reunion.model.Emprunt;
import com.reunion.model.Membre;
import com.reunion.model.Ronde;
import com.reunion.util.CalendarUtils;
import com.reunion.util.SessionUtil;

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
	private boolean isEditable;
	private double fondDeCaisse;

	public void init() {
		empruntService.startConversation();
		if (membres == null) {
			membres = membreService.findAll();
		}
		emprunts = empruntService.toutLesEmprunts();
		for (Emprunt emprunt : emprunts) {
			if (CalendarUtils.date1BeforeDate2(emprunt.getDateDeRemboursement(), new Date(), true)) {
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
		if (isEditable) {
			RequestContext.getCurrentInstance().execute(
					"alert('Veuillez d´abord finir avec l´edition d´un emprunt avant de commencer un autre.');");
			return;
		} else {

			isEditable = true;
			this.emprunt = emprunt;
			RequestContext.getCurrentInstance().execute("$('#js_editerOuRembourserModal').modal('show');");
		}
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
		RequestContext.getCurrentInstance().execute("$('#js_creerEmpruntModal').modal('show');");
	}

	public String rembourser(boolean activer) {
		StatusDeRemboursement statusDeRemboursement = (activer) ? StatusDeRemboursement.REMBOURSE
				: StatusDeRemboursement.OUVERT;
		Date dateDeRemboursement = (activer) ? new Date() : emprunt.getDateDeRemboursement();
		this.emprunt.setDateDeRemboursement(dateDeRemboursement);
		this.emprunt.setStatus(statusDeRemboursement);
		return sauve();
	}

	public Float calculDuBenefice() {
		Float benefice = 0.f;
		for (Emprunt emprunt : emprunts) {
			benefice += calculDuRemboursement(emprunt) - emprunt.getSommeEmpruntee();
		}
		return benefice;
	}

	public String empruntEditable(boolean editable) {
		this.emprunt.setEditable(editable);
		isEditable = editable;
		return Pages.EMPRUNT;
	}

	public double calculDuFondDeCaisse() {
//		HttpSession session = SessionUtil.getSession();
//		Membre membreActuel = (Membre) session.getAttribute(SessionUtil.MEMBRE_ACTUEL);
//		fondDeCaisse = membres.stream().filter(m -> {
//			return (m.getGroupe() != null && m.getGroupe().getId() == membreActuel.getGroupe().getId());
//		}).mapToDouble(m -> m.getFondDeCaisse()).sum();
//		for (Emprunt emprunt : emprunts) {
//			fondDeCaisse -= emprunt.getSommeEmpruntee();
//		}
		return fondDeCaisse;
	}

	public Float calculDuRemboursement(Emprunt emprunt) {
		return (emprunt != null) ? (emprunt.getSommeEmpruntee() + emprunt.getSommeEmpruntee() * 0.02f) : 0;
	}

	public String suprimerLeRemboursement(Emprunt emprunt) {
		empruntService.delete(emprunt.getId());
		return Pages.EMPRUNT;
	}

	public String sauvegarder() {
		if (fondDeCaisse < emprunt.getSommeEmpruntee()) {
			Helper.showError("La somme que vous voulez emprunter est au dessus du capital disponible", "errors");
			return Pages.SELF;
		} else {
			isEditable = false;
			emprunt.setEditable(false); // toujours
			if (emprunt.getStatus() == StatusDeRemboursement.OUVERT) {
				emprunt.setDateDeLemprunt(new Date());// aujourdhui
			}
			return sauve();
		}

	}

	public String sauvegarderEdit() {
		if (fondDeCaisse < emprunt.getSommeEmpruntee()) {
			Helper.showError("La somme que vous voulez emprunter est au dessus du capital disponible", "errors");
			return Pages.SELF;
		} else {
			isEditable = false;
			emprunt.setEditable(false); // toujours
			return sauve();
		}

	}
	
	private String sauve() {
		Emprunt res = empruntService.createEmprunt(emprunt);
		if (res != null) {
			LOG.info("L´Eprunt " + res.toString() + " a été creer");
		}
		return Pages.EMPRUNT;
	}

	public String close() {
		this.emprunt = null;
		return Pages.EMPRUNT;
	}
}
