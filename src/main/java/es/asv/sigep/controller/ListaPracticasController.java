package es.asv.sigep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.asv.sigep.dto.PracticaDTO;
import es.asv.sigep.service.OrganizacionService;
import es.asv.sigep.service.PracticaService;

@Controller
public class ListaPracticasController {

	@Autowired 
	private PracticaService practicaService;	
	
	
	@GetMapping("/practicas")
	public String inicio(Model model) {

		ControllerUtils.modelFooter(model);
		
		List<PracticaDTO> practicas = practicaService.findAllByTutor(ControllerUtils.obtenerUsuario().getId());
		

		model.addAttribute("practicas", practicas);
		return "listaPracticas";

	}

}
