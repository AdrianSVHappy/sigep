package es.asv.sigep.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListaPracticasController {

	@GetMapping("/practicas")
	public String inicio(Model model) {

		ControllerUtils.modelFooter(model);

		return "listaPracticas";

	}

}
