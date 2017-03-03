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
import com.reunion.model.Membre;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
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

	@Column(length = 20, name = "debutDeLaRonde")
	@Temporal(TemporalType.DATE)
	private Date debutDeLaRonde;

	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
	private Set<Membre> membres = new HashSet<Membre>();

	@Column(length = 15, name = "finDeLaRonde")
	@Temporal(TemporalType.DATE)
	private Date finDeLaRonde;

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

	
	public Date getDebutDeLaRonde() {
		return debutDeLaRonde;
	}

	public void setDebutDeLaRonde(Date debutDeLaRonde) {
		this.debutDeLaRonde = debutDeLaRonde;
	}

	public Set<Membre> getMembres() {
		return this.membres;
	}

	public void setMembres(final Set<Membre> membres) {
		this.membres = membres;
	}

	public Date getFinDeLaRonde() {
		return finDeLaRonde;
	}

	public void setFinDeLaRonde(Date finDeLaRonde) {
		this.finDeLaRonde = finDeLaRonde;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id != null)
			result += "id: " + id;
		result += ", version: " + version;
		if (debutDeLaRonde != null)
			result += ", debutDeLaRonde: " + debutDeLaRonde;
		if (membres != null)
			result += ", membres: " + membres;
		if (finDeLaRonde != null)
			result += ", finDeLaRonde: " + finDeLaRonde;
		return result;
	}
}