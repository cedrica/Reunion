package com.reunion.controller;

import java.util.List;

import com.reunion.bean.AbstractModalBean;
import com.reunion.common.Pages;

public abstract class BaseModalController<ENTITY, BEAN extends AbstractModalBean<ENTITY>> extends BaseController {
	protected abstract BEAN getBean();

	protected abstract ENTITY getNewEntity();

	public String abbrechen() {
		BEAN vo = getBean();
		vo.setShowModal(false);
		return Pages.SELF;
	}


	public String edit(Long id) {
		BEAN vo = getBean();
		vo.setShowModal(true);
		LOG.debug("edit VO " + id);
		return Pages.SELF;
	}


	
	protected abstract ENTITY getById(Long id);

	protected boolean validate(ENTITY entity) {
		return true;
	}

	protected abstract List<ENTITY> getList();

}