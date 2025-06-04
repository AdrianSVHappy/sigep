package es.asv.sigep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.dto.PracticaDTO;
import es.asv.sigep.enums.RolEnum;
import es.asv.sigep.service.OrganizacionService;
import es.asv.sigep.service.PracticaService;

@Controller
@RequestMapping("/practica")
public class PracticasController {

	@Autowired 
	private PracticaService practicaService;	
	
	/**
	 * Muesta la pantalla de lista de practicas
	 * @param model
	 * @return vista
	 */
	private String mostrarTabla(Model model) {
		
		ControllerUtils.modelFooter(model);
		
		return "practica/listaPracticas";
	}
	
	
	/**
	 * Muesta la pantalla de detalle  de una practica
	 * @param model
	 * @return vista
	 */
	private String mostarDetalle(PracticaDTO practica, Model model) {
		
		ControllerUtils.modelFooter(model);
		ControllerUtils.modelPersona(model);
		
		
		model.addAttribute("practica", practica);
		
		return "practica/detallePractica";
		
	}
	
	@GetMapping("/practicas")
	public String inicio(Model model) {
		
		List<PracticaDTO> practicas = practicaService.findAllByTutor(ControllerUtils.obtenerUsuario().getId());
		

		model.addAttribute("practicas", practicas);
		
		return mostrarTabla(model);
	}
	
	@GetMapping("/detalle/{id}")
	public String detalle(@PathVariable("id") final Long id, final Model model) {
	
		
		PersonaDTO aux = ControllerUtils.obtenerUsuario();
		PracticaDTO practica = null;
		
	
		//Alumno
		if(ControllerUtils.obtenerUsuario().getRol() == RolEnum.E ) {
			practica = practicaService.findByAlumno(ControllerUtils.obtenerUsuario().getId());
		}
		
		//Profesor
		if(ControllerUtils.obtenerUsuario().getRol() == RolEnum.P) {
			practica = practicaService.findById(id);
		}
		
		
		//Practica no encontrada
		if(practica != null) {
			return mostarDetalle(practica, model);
		}
	
		
		//TODO Mostrar mensaje de error
		//Error de permiso
		return ControllerUtils.mostarError(0, model);
		
	}
	
	
	

}
