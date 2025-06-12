package es.asv.sigep.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.asv.sigep.service.PersonaService;



@Controller
public class HomeController {

	
	@Autowired
	private PersonaService personaService;
	
	
	@GetMapping("/")
	public String inicio(Model model ){

		ControllerUtils.modelFooter(model);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		
		model.addAttribute("persona", personaService.findByEmail(auth.getName()));
		
		//ControllerUtils.modelPersona(personaService, model);
		
		return "home";
	}
	

	
}

