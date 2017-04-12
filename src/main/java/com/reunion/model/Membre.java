package com.reunion.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import com.reunion.model.DonneesBanquaire;
import com.reunion.enums.RoleType;
import javax.persistence.Enumerated;

@Entity
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

	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE,
			CascadeType.REFRESH})
	private Adresse adresse;

	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE,
			CascadeType.REFRESH})
	private Contact contact;

	@ManyToOne(cascade = {CascadeType.REFRESH})
	private Groupe groupe;

	@Column(length = 50, name = "motDePass")
	private String motDePass;

	@Column(length = 1, name = "editable")
	private boolean editable;

	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE,
			CascadeType.REFRESH})
	private DonneesBanquaire donneesBanquaire;

	@Column(length = 2, name = "fondDeCaisse")
	private double fondDeCaisse;

	@Column(name = "monImage")
	@Lob
	private byte[] monImage;

	@Enumerated
	@Column(name = "role")
	private RoleType role;

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

	public String getMotDePass() {
		return motDePass;
	}

	public void setMotDePass(String motDePass) {
		this.motDePass = motDePass;
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

	public boolean getEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public DonneesBanquaire getDonneesBanquaire() {
		return donneesBanquaire;
	}

	public void setDonneesBanquaire(DonneesBanquaire donneesBanquaire) {
		this.donneesBanquaire = donneesBanquaire;
	}

	public double getFondDeCaisse() {
		return fondDeCaisse;
	}

	public void setFondDeCaisse(double fondDeCaisse) {
		this.fondDeCaisse = fondDeCaisse;
	}

	public byte[] getMonImage() {
		return monImage;
	}

	public void setMonImage(byte[] monImage) {
		this.monImage = monImage;
	}

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}


	@Override
	public String toString() {
		return nom+" "+prenom;
	}

}