package com.reunion.model;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@ManagedBean
@Table(name = "Membre")
public class Membre implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@Column(length = 20, name = "nom")
	private String nom;

	@Column(length = 20, name = "prenom")
	private String prenom;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE, CascadeType.REFRESH})
	private Adresse adresse;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE, CascadeType.REFRESH})
	private Contact contact;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH, CascadeType.REMOVE})
	private Groupe groupe;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE, CascadeType.REFRESH})
	private Trafique trafique;

	@Column(length = 50, name = "motDePass")
	private String motDePass;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Membre)) {
			return false;
		}
		Membre other = (Membre) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	public Trafique getTrafique() {
		return trafique;
	}

	public void setTrafique(Trafique trafique) {
		this.trafique = trafique;
	}

	public String getMotDePass() {
		return motDePass;
	}

	public void setMotDePass(String motDePass) {
		this.motDePass = motDePass;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id != null)
			result += "id: " + id;
		result += ", version: " + version;
		if (nom != null && !nom.trim().isEmpty())
			result += ", nom: " + nom;
		if (prenom != null && !prenom.trim().isEmpty())
			result += ", prenom: " + prenom;
		if (adresse != null)
			result += ", adresse: " + adresse;
		if (contact != null)
			result += ", contact: " + contact;
		if (groupe != null)
			result += ", groupe: " + groupe;
		if (trafique != null)
			result += ", trafique: " + trafique;
		if (motDePass != null && !motDePass.trim().isEmpty())
			result += ", motDePass: " + motDePass;
		return result;
	}
}