package com.reunion.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.reunion.util.CalendarUtils;
import com.reunion.model.Trafique;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
@Table(name = "Ronde")
public class Ronde implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@Column(name = "debutDeLaRonde")
	private Date debutDeLaRonde;

	@Column(name = "finDeLaRonde")
	private Date finDeLaRonde;

//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "Ronde_Membre", joinColumns = {@JoinColumn(name = "Ronde_ID")}, inverseJoinColumns = {@JoinColumn(name = "Membre_ID")})
//	private Set<Membre> membres = new HashSet<Membre>();

	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,
			CascadeType.REMOVE, CascadeType.REFRESH})
	private Set<Trafique> trafiques = new HashSet<Trafique>();

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

	public Date getDebutDeLaRonde() {
		return debutDeLaRonde;
	}

	public void setDebutDeLaRonde(Date debutDeLaRonde) {
		this.debutDeLaRonde = debutDeLaRonde;
	}

	public Date getFinDeLaRonde() {
		return finDeLaRonde;
	}

	public void setFinDeLaRonde(Date finDeLaRonde) {
		this.finDeLaRonde = finDeLaRonde;
	}

//	public Set<Membre> getMembres() {
//		return this.membres;
//	}
//
//	public void setMembres(final Set<Membre> membres) {
//		this.membres = membres;
//	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Ronde)) {
			return false;
		}
		Ronde other = (Ronde) obj;
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

	@Override
	public String toString() {
		return CalendarUtils.getShortDayMonthYear(debutDeLaRonde) + " - "
				+ CalendarUtils.getShortDayMonthYear(finDeLaRonde);
	}

	public Set<Trafique> getTrafiques() {
		return this.trafiques;
	}

	public void setTrafiques(final Set<Trafique> trafiques) {
		this.trafiques = trafiques;
	}

}