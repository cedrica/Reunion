package com.reunion.helper;

import java.io.File;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.reunion.business.MembreService;
import com.reunion.model.Membre;

public class GenerateurDeMembres {

	@Inject
	private MembreService membreService;
	public void read() {
		try {

			File file = new File(GenerateurDeMembres.class.getResource("/data.xml").toExternalForm());
			JAXBContext jaxbContext = JAXBContext.newInstance(Membre.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Data data = (Data) jaxbUnmarshaller.unmarshal(file);
			List<Membre> membres = data.getMembres();
			
			for (Membre membre : membres) {
				membreService.createMembre(membre);
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}
}
