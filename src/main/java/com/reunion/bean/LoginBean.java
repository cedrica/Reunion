package com.reunion.bean;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reunion.business.GroupeService;
import com.reunion.business.LoginService;
import com.reunion.business.MembreService;
import com.reunion.common.Pages;
import com.reunion.enums.RoleType;
import com.reunion.model.Adresse;
import com.reunion.model.Contact;
import com.reunion.model.FondDeCaisseParAnnee;
import com.reunion.model.Groupe;
import com.reunion.model.Membre;
import com.reunion.util.CalendarUtils;
import com.reunion.util.SessionUtil;

@Named
@ConversationScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LoginService loginService;
	@Inject
	private MembreService membreService;
	@Inject
	private GroupeService groupeService;

	private String email;
	private String motDepass;
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	private Groupe groupe;
	private Membre membreActuel;

	public void init() {
		loginService.startConversation();
		membreService.startConversation();
		groupeService.startConversation();
		LOG.info("Coversation déclachée");
		List<Membre> membreActuels = membreService.findAll();
		if (membreActuels == null || membreActuels.isEmpty()) {
			Membre membreActuel = new Membre();
			membreActuel.setMotDePass("a");
			membreActuel.setNom("Leumaleu");
			membreActuel.setPrenom("Cedric");

			FondDeCaisseParAnnee fondDeCaisseParAnnee1 = new FondDeCaisseParAnnee();
			fondDeCaisseParAnnee1.setAnnee(CalendarUtils.localDateToDate(LocalDate.of(2100, 3, 2)));
			fondDeCaisseParAnnee1.setFondDeCaisse(200.f);

			FondDeCaisseParAnnee fondDeCaisseParAnnee2 = new FondDeCaisseParAnnee();
			fondDeCaisseParAnnee2.setAnnee(CalendarUtils.localDateToDate(LocalDate.of(2000, 2, 1)));
			fondDeCaisseParAnnee2.setFondDeCaisse(20.f);

			List<FondDeCaisseParAnnee> hsFondDeCaisseParAnnee = new ArrayList<>();
			hsFondDeCaisseParAnnee.add(fondDeCaisseParAnnee1);
			hsFondDeCaisseParAnnee.add(fondDeCaisseParAnnee2);

			membreActuel.setFondDeCaissParAnnee(hsFondDeCaisseParAnnee);
			Adresse adresse = new Adresse();
			adresse.setNumero("129");
			adresse.setPlz(90441);
			adresse.setRue("Schweinauer Hauptstrasse");
			adresse.setVille("Nürnberg");
			membreActuel.setAdresse(adresse);

			Contact contact = new Contact();
			contact.setEmail("djikeussi2001@yahoo.fr");
			contact.setTelephone("017663112957");
			membreActuel.setContact(contact);

			Groupe groupe = new Groupe();
			groupe.setNom("Erlangen");
			groupe = groupeService.createGroupe(groupe);

			membreService.startConversation();
			membreActuel.setGroupe(groupe);
			membreActuel = membreService.createMembre(membreActuel);

			membreActuel = new Membre();
			membreActuel.setMotDePass("b");
			membreActuel.setNom("Kemoue");
			membreActuel.setPrenom("Silas");

			fondDeCaisseParAnnee1 = new FondDeCaisseParAnnee();
			fondDeCaisseParAnnee1.setAnnee(CalendarUtils.localDateToDate(LocalDate.of(2100, 3, 2)));
			fondDeCaisseParAnnee1.setFondDeCaisse(200.f);

			fondDeCaisseParAnnee2 = new FondDeCaisseParAnnee();
			fondDeCaisseParAnnee2.setAnnee(CalendarUtils.localDateToDate(LocalDate.of(2000, 2, 1)));
			fondDeCaisseParAnnee2.setFondDeCaisse(20.f);

			hsFondDeCaisseParAnnee = new ArrayList<>();
			hsFondDeCaisseParAnnee.add(fondDeCaisseParAnnee1);
			hsFondDeCaisseParAnnee.add(fondDeCaisseParAnnee2);

			membreActuel.setFondDeCaissParAnnee(hsFondDeCaisseParAnnee);
			adresse = new Adresse();
			adresse.setNumero("127");
			adresse.setPlz(90441);
			adresse.setRue("Bissingerstrasse");
			adresse.setVille("Erlangen");
			membreActuel.setAdresse(adresse);

			contact = new Contact();
			contact.setEmail("silas@yahoo.fr");
			contact.setTelephone("017663113357");
			membreActuel.setContact(contact);

			membreActuel.setGroupe(groupe);
			membreActuel = membreService.createMembre(membreActuel);
			membreService.startConversation();

			membreActuel = new Membre();
			membreActuel.setMotDePass("c");
			membreActuel.setNom("Komge");
			membreActuel.setPrenom("marc");

			fondDeCaisseParAnnee1 = new FondDeCaisseParAnnee();
			fondDeCaisseParAnnee1.setAnnee(CalendarUtils.localDateToDate(LocalDate.of(2100, 3, 2)));
			fondDeCaisseParAnnee1.setFondDeCaisse(200.f);

			fondDeCaisseParAnnee2 = new FondDeCaisseParAnnee();
			fondDeCaisseParAnnee2.setAnnee(CalendarUtils.localDateToDate(LocalDate.of(2000, 2, 1)));
			fondDeCaisseParAnnee2.setFondDeCaisse(20.f);

			hsFondDeCaisseParAnnee = new ArrayList<>();
			hsFondDeCaisseParAnnee.add(fondDeCaisseParAnnee1);
			hsFondDeCaisseParAnnee.add(fondDeCaisseParAnnee2);

			membreActuel.setFondDeCaissParAnnee(hsFondDeCaisseParAnnee);

			adresse = new Adresse();
			adresse.setNumero("127");
			adresse.setPlz(90441);
			adresse.setRue("Manfredstrasse");
			adresse.setVille("Erlangen");
			membreActuel.setAdresse(adresse);
			membreActuel.setRole(RoleType.ADMIN);

			contact = new Contact();
			contact.setEmail("marc@yahoo.fr");
			contact.setTelephone("017345113357");
			membreActuel.setContact(contact);

			membreActuel.setGroupe(groupe);
			membreActuel = membreService.createMembre(membreActuel);
			groupe.getMembres().add(membreActuel);
		}
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	public Groupe getGroupe() {
		return this.groupe;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotDepass() {
		return motDepass;
	}

	public void setMotDepass(String motDepass) {
		this.motDepass = motDepass;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Membre getmembreActuel() {
		return membreActuel;
	}

	public void setmembreActuel(Membre membreActuel) {
		this.membreActuel = membreActuel;
	}

	public String nomDuMembre() {
		HttpSession session = SessionUtil.getSession();
		membreActuel = (Membre) session.getAttribute(SessionUtil.MEMBRE_ACTUEL);
		return membreActuel.getPrenom() + " " + membreActuel.getNom();
	}

	public String seConnecter() throws IOException {
		loginService.stopperLaConversation();
		membreActuel = loginService.findMembreByEmail(email.trim());
		if (membreActuel != null && membreActuel.getMotDePass().equals(motDepass)) {
			HttpSession session = SessionUtil.getSession();
			session.setAttribute(SessionUtil.MEMBRE_ACTUEL, membreActuel);
			setGroupe(membreActuel.getGroupe());
			return Pages.REGLEMENT_INTERIEUR;
		}
		return Pages.INDEX;
	}

	public String logout() throws IOException {
		HttpSession session = SessionUtil.getSession();
		session.removeAttribute(SessionUtil.MEMBRE_ACTUEL);
		session.invalidate();
		FacesContext.getCurrentInstance().getExternalContext().redirect(
				FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
		return Pages.INDEX;
	}

	public String monAvatar() {
		System.out.println("bytes = " + convertFileToBase64(membreActuel.getMonImage()));
		return "data:image/png;base64," + convertFileToBase64(membreActuel.getMonImage());
	}

	public static String convertFileToBase64(byte[] bytes) {
		return (bytes != null) ? Base64.getEncoder().encodeToString(bytes) : null;
	}

}
