package es.asv.sigep.controller;

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
import es.asv.sigep.SigepApplication;
import es.asv.sigep.dto.OrganizacionDTO;
import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.dto.PracticaDTO;
import es.asv.sigep.dto.UbicacionDTO;
import es.asv.sigep.enums.RolEnum;
import es.asv.sigep.enums.TipoEnum;
import es.asv.sigep.service.OrganizacionService;
import es.asv.sigep.service.PersonaService;
import es.asv.sigep.service.PracticaService;

@Controller
@RequestMapping("/practica")
public class PracticasController {

	private final SigepApplication sigepApplication;

	@Autowired
	private PracticaService practicaService;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private OrganizacionService organizacionService;

	@Autowired
	private UbicacionService ubicacionService;

	PracticasController(SigepApplication sigepApplication) {
		this.sigepApplication = sigepApplication;
	}

	/**
	 * Muesta la pantalla de lista de practicas
	 * 
	 * @param model
	 * @return vista
	 */
	private String mostrarTabla(Model model) {

		ControllerUtils.modelFooter(model);
		ControllerUtils.modelPersona(model);

		// Mostrando lista de practicas
		List<PracticaDTO> practicas = practicaService.findAllByTutor(ControllerUtils.obtenerUsuario());
		model.addAttribute("practicas", practicas);

		return "practica/listaPracticas";
	}

	/**
	 * Muesta la pantalla de detalle de una practica
	 * 
	 * @param model
	 * @return vista
	 */
	private String mostarDetalle(PracticaDTO practica, Model model) {

		ControllerUtils.modelFooter(model);
		ControllerUtils.modelPersona(model);

		model.addAttribute("practica", practica);

		return "practica/detallePractica";

	}

	/**
	 * Muesta la pantalla de creación de una practica
	 * 
	 * @param practicaBlanca practica en blanco
	 * @param model
	 * @return vista
	 */
	private String mostrarFormularioNuevo(PracticaDTO practicaBlanca, Model model) {

		ControllerUtils.modelFooter(model);
		ControllerUtils.modelPersona(model);

		// Practica en blanco
		model.addAttribute("practicaForm", practicaBlanca);

		// Lista de responsables
		List<PersonaDTO> listaResponsables = personaService.findAllByRol(RolEnum.R);
		model.addAttribute("listaResponsables", listaResponsables);

		// Lista de empresas
		List<OrganizacionDTO> listaEmpresas = organizacionService.findAllByTipo(TipoEnum.E);
		model.addAttribute("listaEmpresas", listaEmpresas);

		return "practica/nueva";
	}

	@GetMapping("/practicas")
	public String inicio(Model model) {

		return mostrarTabla(model);
	}

	@GetMapping("/detalle/{id}")
	public String detalle(@PathVariable("id") final Long id, final Model model) {

		PersonaDTO aux = ControllerUtils.obtenerUsuario();
		PracticaDTO practica = null;

		// Alumno
		if (ControllerUtils.obtenerUsuario().getRol() == RolEnum.E) {
			practica = practicaService.findByAlumno(ControllerUtils.obtenerUsuario().getId());
		}

		// Profesor
		if (ControllerUtils.obtenerUsuario().getRol() == RolEnum.P) {
			practica = practicaService.findById(id);
		}

		// Practica no encontrada
		if (practica != null) {
			return mostarDetalle(practica, model);
		}

		// TODO Mostrar mensaje de error
		// Error de permiso
		return ControllerUtils.mostarError(0, model);

	}

	@GetMapping("/nueva")
	public String nuevaPractica(Model model) {

		if (ControllerUtils.obtenerUsuario().getRol() == RolEnum.P) {
			PracticaDTO practicaBlanca = new PracticaDTO();

			// Añadimos un alumno en blanco
			PersonaDTO alumnoBlanco = new PersonaDTO();
			alumnoBlanco.setRol(RolEnum.E);
			alumnoBlanco.setUbicacion(new UbicacionDTO());
			alumnoBlanco.setOrganizacion(ControllerUtils.obtenerUsuario().getOrganizacion());
			practicaBlanca.setAlumno(alumnoBlanco);

			// Añadimos tutor
			practicaBlanca.setTutor(ControllerUtils.obtenerUsuario());

			// Añadimos Centro
			practicaBlanca.setCentro(ControllerUtils.obtenerUsuario().getOrganizacion());

			// Añadimos empresa en blanco
			OrganizacionDTO empresaBlanca = new OrganizacionDTO();
			empresaBlanca.setTipo(TipoEnum.E);
			empresaBlanca.setUbicacion(new UbicacionDTO());

			// Añadimos responsable en blanco
			PersonaDTO responsableBlanco = new PersonaDTO();
			responsableBlanco.setRol(RolEnum.R);
			responsableBlanco.setUbicacion(empresaBlanca.getUbicacion());

			return mostrarFormularioNuevo(practicaBlanca, model);
		}

		// TODO Mostrar mensaje de error
		// Error de permiso
		return ControllerUtils.mostarError(0, model);

	}

	@PostMapping("/guardar")
	public String guardar(@ModelAttribute("practicaForm") PracticaDTO practicaForm, Model model) {

		// Comprobar que el usuario logueado tiene permiso
		if (ControllerUtils.obtenerUsuario().getRol() != RolEnum.P) {
			// TODO Mostar error de permisos
			return ControllerUtils.mostarError(0, model);
		}

		// Comrobar si hay datos obligatorios nulos
		if (practicaForm == null || practicaForm.getDuracion() < 0 || practicaForm.getInicio() == null
				|| practicaForm.getFin() == null) {
			// TODO Mostrar error campos obligatorios vacios
			return ControllerUtils.mostarError(0, model);
		}

		// Comprobar que la fecha de inicio y fin son correctas
		if (practicaForm.getFin().isBefore(practicaForm.getInicio())) {
			// TODO Mostrar error la fecha de inicio es postarior a la de fin
			return ControllerUtils.mostarError(0, model);
		}

		//Comprobar si el numero de la seguridad social está duplicado
		if(practicaService.existsBynumeroSeguridadSocial(practicaForm.getNumeroSeguridadSocial())) {
			// TODO Mostrar error el numero de la seguridad social ya está registrado
			return ControllerUtils.mostarError(0, model);
		}
		
		// Establecer responsable predefinido
		if (practicaForm.getResponsable().getId() != null) {

			PersonaDTO tutorDB = personaService.findById(practicaForm.getResponsable().getId());

			if (tutorDB == null) {
				// TODO Mostrar error el tutor no existe en la base de datos
				return ControllerUtils.mostarError(0, model);
			}

			practicaForm.setResponsable(tutorDB);
			practicaForm.setEmpresa(tutorDB.getOrganizacion());

		}

		// Establecer empresa predefinida
		else if (practicaForm.getEmpresa().getId() != null) {

			OrganizacionDTO empresaDB = organizacionService.findById(practicaForm.getEmpresa().getId());

			if (empresaDB == null) {
				// TODO Mostrar error la empresa no existe en la base de datos
				return ControllerUtils.mostarError(0, model);
			}

			practicaForm.setEmpresa(empresaDB);
			practicaForm.getResponsable().setUbicacion(empresaDB.getUbicacion());

		}
			
		// Establecer datos constantes
		practicaForm.setTutor(ControllerUtils.obtenerUsuario());
		practicaForm.setCentro(ControllerUtils.obtenerUsuario().getOrganizacion());
		practicaForm.getAlumno().setNombre(practicaForm.getAlumno().getEmail());
		practicaForm.getAlumno().setUbicacion(ControllerUtils.obtenerUsuario().getOrganizacion().getUbicacion());
		practicaForm.getAlumno().setOrganizacion(ControllerUtils.obtenerUsuario().getOrganizacion());
		practicaForm.getAlumno().setRol(RolEnum.E);

		// Guardamos los campos nuevos
		if (practicaForm.getEmpresa().getId() == null) {
			practicaForm.getEmpresa().setTipo(TipoEnum.E);
		}
		if (practicaForm.getResponsable().getId() == null) {
			practicaForm.getResponsable().setRol(RolEnum.R);
			practicaForm.getResponsable().setOrganizacion(practicaForm.getEmpresa());
			practicaForm.getResponsable().setUbicacion(practicaForm.getEmpresa().getUbicacion());
		}

		// personaService.save(practicaForm.getAlumno());
		practicaService.save(practicaForm);

		return mostrarTabla(model);
	}

}
