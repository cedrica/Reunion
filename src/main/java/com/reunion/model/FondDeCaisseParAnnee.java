package com.reunion.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.util.Date;

@Entity
@Table(name = "FondDeCaisseParAnnee")
public class FondDeCaisseParAnnee implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@Column(length = 10, name = "fondDeCaisse")
	private Float fondDeCaisse;

	@Column(length = 15, name = "annee")
	private Date annee;

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


	public Float getFondDeCaisse() {
		return fondDeCaisse;
	}

	public void setFondDeCaisse(Float fondDeCaisse) {
		this.fondDeCaisse = fondDeCaisse;
	}

	public Date getAnnee() {
		return annee;
	}

	public void setAnnee(Date annee) {
		this.annee = annee;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id != null)
			result += "id: " + id;
		result += ", version: " + version;
		result += ", fondDeCaisse: " + fondDeCaisse;
		if (annee != null)
			result += ", annee: " + annee;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FondDeCaisseParAnnee other = (FondDeCaisseParAnnee) obj;
		if (annee == null) {
			if (other.annee != null)
				return false;
		} else if (!annee.equals(other.annee))
			return false;
		if (fondDeCaisse == null) {
			if (other.fondDeCaisse != null)
				return false;
		} else if (!fondDeCaisse.equals(other.fondDeCaisse))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((annee == null) ? 0 : annee.hashCode());
		result = prime * result + ((fondDeCaisse == null) ? 0 : fondDeCaisse.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + version;
		return result;
	}
}