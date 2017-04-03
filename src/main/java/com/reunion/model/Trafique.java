package com.reunion.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import com.reunion.model.Membre;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;

@Entity
@Table(name = "Trafique")
public class Trafique implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@Column(length = 20, name = "dateDeBouffe")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateDeBouffe;

	@Column(length = 10, name = "ristourne")
	private Float ristourne;

	@Column(length = 10, name = "supplement")
	private Float supplement;

	@Column(length = 20, name = "cotisation")
	private Float cotisation;

	@Column(length = 10, name = "rang")
	private int rang;

	@OneToOne(cascade = CascadeType.MERGE)
	private Membre membre;

	@Column(length = 1, name = "editable")
	private boolean editable;

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
		if (!(obj instanceof Trafique)) {
			return false;
		}
		Trafique other = (Trafique) obj;
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


	public Date getDateDeBouffe() {
		return dateDeBouffe;
	}

	public void setDateDeBouffe(Date dateDeBouffe) {
		this.dateDeBouffe = dateDeBouffe;
	}

	public Float getRistourne() {
		return ristourne;
	}

	public void setRistourne(Float ristourne) {
		this.ristourne = ristourne;
	}

	public Float getSupplement() {
		return supplement;
	}

	public void setSupplement(Float supplement) {
		this.supplement = supplement;
	}

	public Float getCotisation() {
		return cotisation;
	}

	public void setCotisation(Float cotisation) {
		this.cotisation = cotisation;
	}

	public int getRang() {
		return rang;
	}

	public void setRang(int rang) {
		this.rang = rang;
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}

	public boolean getEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id != null)
			result += "id: " + id;
		result += ", version: " + version;
		if (dateDeBouffe != null)
			result += ", dateDeBouffe: " + dateDeBouffe;
		if (ristourne != null)
			result += ", ristourne: " + ristourne;
		if (supplement != null)
			result += ", supplement: " + supplement;
		if (cotisation != null)
			result += ", cotisation: " + cotisation;
		result += ", rang: " + rang;
		if (membre != null)
			result += ", membre: " + membre;
		result += ", editable: " + editable;
		return result;
	}

}