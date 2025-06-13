package es.asv.sigep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import es.asv.sigep.dto.OrganizacionDTO;
import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.dto.PracticaDTO;
import es.asv.sigep.dto.UbicacionDTO;
import es.asv.sigep.enums.RolEnum;
import es.asv.sigep.enums.TipoEnum;
import es.asv.sigep.service.OrganizacionService;
import es.asv.sigep.service.PersonaService;

@Controller
public class HomeController {

	@Autowired
	private PersonaService personaService;

	@Autowired
	private OrganizacionService organizacionService;

	@GetMapping("/")
	public String inicio(Model model) {

		ControllerUtils.modelFooter(model);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		PersonaDTO per = personaService.findByEmail(auth.getName());

		model.addAttribute("persona", per);

		return "home";
	}

	@GetMapping("/registrar")
	public String registro(Model model) {

		List<OrganizacionDTO> organizaciones = organizacionService.findAllByTipo(TipoEnum.I);
		model.addAttribute("organizaciones", organizaciones);

		PersonaDTO reg = new PersonaDTO();
		model.addAttribute("personaForm", reg);

		return "registroForm";
	}

	@PostMapping("/guardarProfe")
	public String guardar(@ModelAttribute("practicaForm") PersonaDTO personaForm, Model model) {

		if (personaForm.getOrganizacion().getId() != null) {
			personaForm.setOrganizacion(organizacionService.findById(personaForm.getOrganizacion().getId()));
		}else {
			personaForm.getOrganizacion().setTipo(TipoEnum.I);
		}

		personaForm.setRol(RolEnum.P);

		UbicacionDTO nueva = personaForm.getOrganizacion().getUbicacion();
		nueva.setId(null);
		personaForm.setUbicacion(nueva);
		
		personaService.save(personaForm);
		
		return "home";
	}

}

//TODO cuando un tutor edita los datos de un resoponsable ya guardado no se actualiza 
//TODO En la lista de alumnos de la vista de enviar mensaje del profesor, los alumnos sin apellidos aparecen en null
//TODO En la edición de alumno, el alumno puede editar su centro, quitar la opción de editar eso
//TODO En la vista de detalle de las practicas, a los alumnos no les puede aparecer el boton de editar
//TODO Pensar en como un tutor purde ver los registros de un alumno
