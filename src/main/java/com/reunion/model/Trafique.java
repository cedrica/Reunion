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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.reunion.model.Ronde;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
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

	@Column(length = 10, name = "fondDeCaisse")
	private String fondDeCaisse;

	@Column(length = 20, name = "dateDeBouffe")
	@Temporal(TemporalType.DATE)
	private Date dateDeBouffe;

	@Column(length = 10, name = "ristourne")
	private Float ristourne;

	@Column(length = 10, name = "cotisation")
	private int cotisation;

	@Column(length = 10, name = "supplement")
	private Float supplement;

	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
	private Set<Ronde> rondes = new HashSet<Ronde>();

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

	public String getFondDeCaisse() {
		return fondDeCaisse;
	}

	public void setFondDeCaisse(String fondDeCaisse) {
		this.fondDeCaisse = fondDeCaisse;
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

	public int getCotisation() {
		return cotisation;
	}

	public void setCotisation(int cotisation) {
		this.cotisation = cotisation;
	}

	public Float getSupplement() {
		return supplement;
	}

	public void setSupplement(Float supplement) {
		this.supplement = supplement;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id != null)
			result += "id: " + id;
		result += ", version: " + version;
		if (fondDeCaisse != null && !fondDeCaisse.trim().isEmpty())
			result += ", fondDeCaisse: " + fondDeCaisse;
		if (dateDeBouffe != null)
			result += ", dateDeBouffe: " + dateDeBouffe;
		if (ristourne != null)
			result += ", ristourne: " + ristourne;
		result += ", cotisation: " + cotisation;
		if (supplement != null)
			result += ", supplement: " + supplement;
		return result;
	}

	public Set<Ronde> getRondes() {
		return this.rondes;
	}

	public void setRondes(final Set<Ronde> rondes) {
		this.rondes = rondes;
	}
}