package es.asv.sigep.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import es.asv.sigep.service.UbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import es.asv.sigep.converter.OrganizacionConverter;
import es.asv.sigep.dto.OrganizacionDTO;
import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.dto.PracticaDTO;
import es.asv.sigep.dto.UbicacionDTO;
import es.asv.sigep.entities.OrganizacionEntity;
import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.entities.UbicacionEntity;
import es.asv.sigep.enums.RolEnum;
import es.asv.sigep.service.OrganizacionService;
import es.asv.sigep.service.PersonaService;
import es.asv.sigep.service.PracticaService;

@Controller
@RequestMapping("/persona")
public class PersonaController {

    private final UbicacionService ubicacionService;

	private final OrganizacionConverter organizacionConverter;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private PracticaService practicaService;

	@Autowired
	private OrganizacionService organizacionService;

	PersonaController(OrganizacionConverter organizacionConverter, UbicacionService ubicacionService) {
		this.organizacionConverter = organizacionConverter;
		this.ubicacionService = ubicacionService;
	}

	/**
	 * Muestra la pantalla dela lista de alumnos
	 * 
	 * @param model
	 * @return vista
	 */
	private String mostrarTabla(Model model) {

		ControllerUtils.modelFooter(model);
		ControllerUtils.modelPersona(personaService, model);

		return "persona/listaAlumnos";
	}

	/**
	 * Muesta la pantalla de detalle de una persona
	 * 
	 * @param alumno Persona de la que se muestan los datos
	 * @param model
	 * @return vista
	 */
	private String mostrarDetalle(PersonaDTO alumno, Model model) {

		ControllerUtils.modelFooter(model);
		ControllerUtils.modelPersona(personaService, model);
		model.addAttribute("alumno", alumno);

		return "persona/detallePersona";
	}

	/**
	 * Muestra la pantalla de formulario de una persona
	 * 
	 * @param personaForm persona de la que se muestra en formulario
	 * @param model
	 * @return vista
	 */
	private String mostrarFormulario(PersonaDTO personaForm, Model model) {

		ControllerUtils.modelFooter(model);
		ControllerUtils.modelPersona(personaService, model);

		// En caso de que sea una persona nueva
		if (personaForm == null)
			personaForm = new PersonaDTO();

		// En caso de que no tenga ubicación asociada
		if (personaForm.getUbicacion() == null)
			personaForm.setUbicacion(new UbicacionDTO());

		model.addAttribute("personaForm", personaForm);

		// Todas las organisaciones
		List<OrganizacionDTO> organizaciones = organizacionService.findAll();
		model.addAttribute("organizaciones", organizaciones);

		return "persona/formulario";

	}

	/**
	 * Lista de alumnos de un profesor
	 * 
	 * @param model
	 * @return vista
	 */
	@GetMapping("/alumnos")
	public String inicio(Model model) {

		List<PracticaDTO> practicas = practicaService.findAllByTutor(ControllerUtils.obtenerUsuario(personaService));
		List<PersonaDTO> alumnos = new ArrayList<>();

		for (PracticaDTO practica : practicas) {

			alumnos.add(practica.getAlumno());
		}

		model.addAttribute("alumnos", alumnos);

		return mostrarTabla(model);
	}

	/**
	 * Busca un alumno
	 * 
	 * @param id    ID de la persona que buscaba
	 * @param model
	 * @return vista
	 */
	@GetMapping("/detalle/{id}")
	public String detalle(@PathVariable("id") final Long id, final Model model) {

		// Propio
		if (ControllerUtils.obtenerUsuario(personaService).getId() == id) {
			return mostrarDetalle(ControllerUtils.obtenerUsuario(personaService), model);
		}

		PersonaDTO alumno = personaService.findById(id);

		// Alumno no encontado
		if (alumno == null) {
			// TODO Mostrar mensaje de error
			return inicio(model);
		}

		// Tutor
		if (ControllerUtils.obtenerUsuario(personaService).getRol() == RolEnum.P
				&& practicaService.existsByTutorAndAlumno(ControllerUtils.obtenerUsuario(personaService), alumno)) {
			return mostrarDetalle(alumno, model);
		}

		// Error de permiso
		return ControllerUtils.mostarError(0, model);
	}

	@GetMapping("/form")
	public String formulario(Model model) {
		return mostrarFormulario(ControllerUtils.obtenerUsuario(personaService), model);
	}

	@PostMapping("/guardar")
	public String guardar(@ModelAttribute("personaForm") PersonaDTO personaForm, Model model) {

		if (personaForm == null) {
			// TODO Hacer error no se ha guardado los datos de la persona correctamente
			return ControllerUtils.mostarError(0, model);
		}

		if (personaForm.getOrganizacion() == null) {
			// TODO Hacer error no se ha guardado los datos de la organizacipn
			return ControllerUtils.mostarError(0, model);
		}

		//Guardamos los datos de la ubucacion
		ubicacionService.save(personaForm.getUbicacion());
		
		//Guardamos los datos de la persona
		personaService.save(personaForm);
		
		//TODO modificar user si se ha modificado el email
		
		return mostrarDetalle(ControllerUtils.obtenerUsuario(personaService), model);
	}
}
