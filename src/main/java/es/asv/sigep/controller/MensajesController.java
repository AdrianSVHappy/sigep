package es.asv.sigep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;

import es.asv.sigep.dto.MensajeDTO;
import es.asv.sigep.service.MensajeService;
import es.asv.sigep.service.PersonaService;

@Controller
@RequestMapping("/mensaje")
public class MensajesController {

	@Autowired
	private MensajeService mensajeService;

	@Autowired
	private PersonaService personaService;

	private String mostrarLista(Model model) {

		ControllerUtils.modelFooter(model);
		ControllerUtils.modelPersona(model);
		
		return "mensaje/listaMensajes";
		
	}
	
	
	@GetMapping("/mensajes")
	public String inicio(Model model) {
		
		List<MensajeDTO> lista = mensajeService.findAllByAutorOrReceptorOrderByFechaDesc(ControllerUtils.obtenerUsuario().getId());
		
		model.addAttribute("listaMensajes", lista);
		
		return mostrarLista(model);
		
		
	}

}
