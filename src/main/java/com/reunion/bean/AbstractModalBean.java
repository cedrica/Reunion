package com.reunion.bean;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reunion.common.Pages;

public abstract class AbstractModalBean <T> implements Serializable {

	private static final long serialVersionUID = 1L;
	protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
	

	private boolean showModal;

	



	public boolean isShowModal() {
		return showModal;
	}

	public void setShowModal(boolean showModal) {
		this.showModal = showModal;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((LOG == null) ? 0 : LOG.hashCode());
		result = prime * result + (showModal ? 1231 : 1237);
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
		AbstractModalBean other = (AbstractModalBean) obj;
		if (LOG == null) {
			if (other.LOG != null)
				return false;
		} else if (!LOG.equals(other.LOG))
			return false;
		if (showModal != other.showModal)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AbstractModalBean [LOG=" + LOG + ", showModal=" + showModal +  "]";
	}


}