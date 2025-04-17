package es.asv.sigep.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String inicio(Model model) {
		
		String email = "adriansuarezvaldayo573@gmail.com";
		String telef = "644 63 44 62";
		String direc = "C\\ Dalí Nº 9";
		
		model.addAttribute("email", email);
		model.addAttribute("telef", telef);
		model.addAttribute("direc", direc);
		
		return "home";
	}
	
}
