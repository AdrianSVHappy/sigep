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

		usuarioAux = personaService.findById(id);
	
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
	
	
	public static void modelPersona(Model model) {
		
		if(usuarioAux != null) {
			model.addAttribute("persona", usuarioAux);
		}
		
	}
	
	
	public static String mostarError(int error, Model model) {
		
		model.addAttribute("codigo", error);
		
		switch (error) {
		case 0: {
			model.addAttribute("mensaje", "No tiene permiso para entrar en esta página");
			return "vistaError";
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + error);
		}
		
	}
	
	
	public static String traducirMes(int codMes) {
		
		  ArrayList<String> meses = new ArrayList<>();
		  
	        meses.add("Enero");
	        meses.add("Febrero");
	        meses.add("Marzo");
	        meses.add("Abril");
	        meses.add("Mayo");
	        meses.add("Junio");
	        meses.add("Julio");
	        meses.add("Agosto");
	        meses.add("Septiembre");
	        meses.add("Octubre");
	        meses.add("Noviembre");
	        meses.add("Diciembre");

	        return meses.get(codMes - 1);
	}

}
