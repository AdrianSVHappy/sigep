package es.asv.sigep.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/alumno")
public class ListaAlumnosController {

	
	@Autowired
	private PersonaService personaService;
	

	@Autowired
	private	PracticaService practicaService;
	

	/**
	 * Muestra la pantalla dela lista de alumnos
	 * @param model
	 * @return vista
	 */
	private String mostrarTabla(Model model) {

		ControllerUtils.modelFooter(model);
		
		return "alumno/listaAlumnos";
	}
	
	
	/**
	 * Muesta la pantalla de detalle  de un alumno
	 * @param model
	 * @return vista
	 */
	private String mostrarDetalle(PersonaDTO alumno, Model model) {
		
		ControllerUtils.modelFooter(model);
		model.addAttribute("alumno", alumno);
		
		return "alumno/detalleAlumno";
	}
	
	
	/**
	 * Lista de alumnos de un profesor
	 * 
	 * @param model
	 * @return vista
	 */
	@GetMapping("/alumnos")
	public String inicio(Model model) {

		List<PracticaDTO> practicas = practicaService.findAllByTutor(ControllerUtils.obtenerUsuario().getId());
		List<PersonaDTO> alumnos = new ArrayList<>();
		
		for(PracticaDTO practica: practicas) {
			
			alumnos.add(practica.getAlumno());
		}
		
		model.addAttribute("alumnos", alumnos);
		
		return mostrarTabla(model);
	}

	
	/**
	 * Busca un alumno
	 * @param id ID de la persona que buscaba
	 * @param model
	 * @return vista	
	 */
	@GetMapping("/detalle/{id}")
	public String detalle(@PathVariable("id") final Long id, final Model model) {
		
		//Alumno
		if(ControllerUtils.obtenerUsuario().getRol() == RolEnum.E  && ControllerUtils.obtenerUsuario().getId() == id) {
			return mostrarDetalle(ControllerUtils.obtenerUsuario(), model);
		}
		
		
		PersonaDTO alumno = personaService.findById(id);
		
		//Alumno no encontado
		if(alumno == null) {
			//TODO Mostrar mensaje de error
			return inicio(model);
		}
		
		//Tutor 
		if(ControllerUtils.obtenerUsuario().getRol() == RolEnum.P &&  practicaService.existsByTutorAndAlumno(ControllerUtils.obtenerUsuario().getId(), id)) {
			return mostrarDetalle(alumno, model);
		}
		
		//Error de permiso
		return ControllerUtils.mostarError(0, model);
	}
	
}
