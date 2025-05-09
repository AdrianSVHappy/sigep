package es.asv.sigep.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.dto.PracticaDTO;
import es.asv.sigep.entities.OrganizacionEntity;
import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.entities.UbicacionEntity;
import es.asv.sigep.enums.RolEnum;
import es.asv.sigep.service.OrganizacionService;
import es.asv.sigep.service.PersonaService;
import es.asv.sigep.service.PracticaService;

@Controller
public class ListaAlumnosController {

	
	@Autowired
	private PersonaService personaService;
	

	@Autowired
	private	PracticaService practicaService;
	

	
	@GetMapping("/alumnos")
	public String inicio(Model model) {

		ControllerUtils.modelFooter(model);
		
		List<PracticaDTO> practicas = practicaService.findAllByTutor(ControllerUtils.obtenerUsuario().getId());
		List<PersonaDTO> alumnos = new ArrayList<>();
		
		for(PracticaDTO practica: practicas) {
			
			alumnos.add(practica.getAlumno());
		}
		
		model.addAttribute("alumnos", alumnos);
		
		return "listaAlumnos";
	}

}
