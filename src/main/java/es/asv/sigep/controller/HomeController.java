package es.asv.sigep.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.service.PersonaService;
import jakarta.servlet.http.HttpSession;



@Controller
public class HomeController {

	
	@Autowired
	private PersonaService personaService;
	
	@GetMapping("/")
	public String inicio(Model model ){

		ControllerUtils.modelFooter(model);
	
		//TODO Cambiarlo por sistema de inicio se deción
		if(ControllerUtils.obtenerUsuario() == null)
			ControllerUtils.iniciarUsuario(1L, personaService);
		
		
		ControllerUtils.modelPersona(model);
		
		return "home";
	}
	
	
	/**
	 * Metodo para cambiar del usuario profesor al alumno y viceverza 
	 * @param model
	 * @return
	 */
	@GetMapping("/cambiarUsuario")
	public String cambiar(Model model) {
		
		if(1L == ControllerUtils.obtenerUsuario().getId())
			ControllerUtils.iniciarUsuario(3L, personaService);
		else
			ControllerUtils.iniciarUsuario(1L, personaService);
			
		ControllerUtils.modelFooter(model);
		ControllerUtils.modelPersona(model);
		
		return "home";
		
	}
	
}

