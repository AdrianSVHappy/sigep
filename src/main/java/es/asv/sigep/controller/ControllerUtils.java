package es.asv.sigep.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.service.PersonaService;


@Controller
public class ControllerUtils {

	@Autowired
	private PersonaService personaService;
	

	public static void modelFooter(Model model) {

		String email = "adriansuarezvaldayo573@gmail.com";
		String telef = "644 63 44 62";
		String direc = "C\\ Dalí Nº 9";

		model.addAttribute("email", email);
		model.addAttribute("telef", telef);
		model.addAttribute("direc", direc);

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



	public static void modelPersona(PersonaService ser, Model model) {
		
		model.addAttribute("persona", obtenerUsuario(ser));
	}



	public static PersonaDTO obtenerUsuario(PersonaService ser) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		 return ser.findByEmail(auth.getName());
	}

}
