package com.reunion.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.reunion.enums.StatusDeRemboursement;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Emprunt")
public class Emprunt implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE, CascadeType.REFRESH})
	private Membre membre;

	@Column(length = 20, name = "sommeEmpruntee")
	private Float sommeEmpruntee;

	@Column(name = "dateDeRemboursement")
	private Date dateDeRemboursement;

	@Column(name = "dateDeLemprunt")
	private Date dateDeLemprunt;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE, CascadeType.REFRESH})
	private Ronde ronde;

	@Enumerated
	@Column(name = "status")
	private StatusDeRemboursement status;

	@Column(name = "empruntRembourseLe")
	@Temporal(TemporalType.TIMESTAMP)
	private Date empruntRembourseLe;

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
		if (!(obj instanceof Emprunt)) {
			return false;
		}
		Emprunt other = (Emprunt) obj;
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

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}

	public Float getSommeEmpruntee() {
		return sommeEmpruntee;
	}

	public void setSommeEmpruntee(Float sommeEmpruntee) {
		this.sommeEmpruntee = sommeEmpruntee;
	}

	public Date getDateDeRemboursement() {
		return dateDeRemboursement;
	}

	public void setDateDeRemboursement(Date dateDeRemboursement) {
		this.dateDeRemboursement = dateDeRemboursement;
	}

	public Date getDateDeLemprunt() {
		return dateDeLemprunt;
	}

	public void setDateDeLemprunt(Date dateDeLemprunt) {
		this.dateDeLemprunt = dateDeLemprunt;
	}

	public Ronde getRonde() {
		return ronde;
	}

	public void setRonde(Ronde ronde) {
		this.ronde = ronde;
	}

	public StatusDeRemboursement getStatus() {
		return status;
	}

	public void setStatus(StatusDeRemboursement status) {
		this.status = status;
	}

	public Date getEmpruntRembourseLe() {
		return empruntRembourseLe;
	}

	public void setEmpruntRembourseLe(Date empruntRembourseLe) {
		this.empruntRembourseLe = empruntRembourseLe;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id != null)
			result += "id: " + id;
		result += ", version: " + version;
		if (membre != null)
			result += ", membre: " + membre;
		if (sommeEmpruntee != null)
			result += ", sommeEmpruntee: " + sommeEmpruntee;
		if (dateDeRemboursement != null)
			result += ", dateDeRemboursement: " + dateDeRemboursement;
		if (dateDeLemprunt != null)
			result += ", dateDeLemprunt: " + dateDeLemprunt;
		if (ronde != null)
			result += ", ronde: " + ronde;
		if (status != null)
			result += ", status: " + status;
		if (empruntRembourseLe != null)
			result += ", empruntRembourseLe: " + empruntRembourseLe;
		return result;
	}
}