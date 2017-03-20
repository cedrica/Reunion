package com.reunion.helper;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.reunion.model.Membre;

@XmlRootElement
public class Data {
	private List<Membre> membres;

	public List<Membre> getMembres() {
		return membres;
	}

	public void setMembres(List<Membre> membres) {
		this.membres = membres;
	}

}
