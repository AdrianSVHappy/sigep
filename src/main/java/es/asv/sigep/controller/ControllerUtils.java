package es.asv.sigep.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.entities.OrganizacionEntity;
import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.entities.PracticaEntity;
import es.asv.sigep.entities.UbicacionEntity;
import es.asv.sigep.enums.TipoEnum;
import es.asv.sigep.service.PersonaService;

public class ControllerUtils {

	private static PersonaDTO usuarioAux;

	public static void iniciarUsuario(Long id, PersonaService personaService) {

		if (usuarioAux == null) {
			usuarioAux = personaService.findById(id);
		}
	}

	public static PersonaDTO obtenerUsuario() {
		return usuarioAux;
	}

	public static void modelFooter(Model model) {

		String email = "adriansuarezvaldayo573@gmail.com";
		String telef = "644 63 44 62";
		String direc = "C\\ Dalí Nº 9";

		model.addAttribute("email", email);
		model.addAttribute("telef", telef);
		model.addAttribute("direc", direc);

	}

}
