package com.reunion.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.reunion.model.Membre;

public class SessionUtil {
	public static final String MEMBRE_ACTUEL = "MEMBRE_ACTUEL";
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static String getUserName() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return session.getAttribute("username").toString();
	}

	public static String getUserId() {
		HttpSession session = getSession();
		if (session != null)
			return (String) session.getAttribute("userid");
		else
			return null;
	}
	
	public static Membre getMembre(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return (Membre) session.getAttribute(MEMBRE_ACTUEL);
	}
}
