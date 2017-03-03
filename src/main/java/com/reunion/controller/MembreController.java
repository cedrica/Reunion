package com.reunion.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.reunion.bean.MembreBean;
import com.reunion.business.MembreService;
import com.reunion.common.Pages;
import com.reunion.model.Membre;

@Named
@RequestScoped
public class MembreController extends BaseModalController<Membre, MembreBean> {
	@Inject
	private MembreBean membreBean;

	@Inject
	MembreService membreService;

	public String save() {
		Membre membre = membreBean.getMembre();
		Membre m = membreService.createMembre(membre);
		if (m != null) {
			LOG.debug("Le membre " + m.getNom() + " a été creer");
		}
		return Pages.MEMBRES;
	}
	
	@Override
	protected Membre getNewEntity() {
		return new Membre();
	}

	@Override
	protected Membre getById(Long id) {
		return null;
	}

	@Override
	protected List<Membre> getList() {
		return null;
	}

	@Override
	protected MembreBean getBean() {
		return membreBean;
	}

}
