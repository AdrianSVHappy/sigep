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
	
		ControllerUtils.iniciarUsuario(1L, personaService);
		
		return "home";
	}
	
}

