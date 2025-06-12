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
		ControllerUtils.modelPersona(personaService, model);

		// Mostrando lista de practicas
		List<PracticaDTO> practicas = practicaService.findAllByTutor(ControllerUtils.obtenerUsuario(personaService));
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
		ControllerUtils.modelPersona(personaService, model);

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
		ControllerUtils.modelPersona(personaService, model);

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

	/**
	 * Mostrar la pantalla de edición de datos de la practica seleccionada
	 * 
	 * @param practica
	 * @param model
	 * @return vista
	 */
	private String mostrarFormulario(PracticaDTO practica, Model model, String tipo) {

		ControllerUtils.modelFooter(model);
		ControllerUtils.modelPersona(personaService, model);

		model.addAttribute("practicaForm", practica);

		if ("D".equals(tipo))
			return "practica/editar";

		if ("E".equals(tipo)) {

			// Lista de responsables
			List<PersonaDTO> listaResponsables = personaService.findAllByRol(RolEnum.R);
			model.addAttribute("listaResponsables", listaResponsables);

			// Lista de empresas
			List<OrganizacionDTO> listaEmpresas = organizacionService.findAllByTipo(TipoEnum.E);
			model.addAttribute("listaEmpresas", listaEmpresas);

			return "practica/editarEmpresa";
		}

		return mostarDetalle(practica, model);
	}

	@GetMapping("/practicas")
	public String inicio(Model model) {

		return mostrarTabla(model);
	}

	@GetMapping("/detalle/{id}")
	public String detalle(@PathVariable("id") final Long id, final Model model) {

		PersonaDTO aux = ControllerUtils.obtenerUsuario(personaService);
		PracticaDTO practica = null;

		// Alumno
		if (ControllerUtils.obtenerUsuario(personaService).getRol() == RolEnum.E) {
			practica = practicaService.findByAlumno(ControllerUtils.obtenerUsuario(personaService).getId());
		}

		// Profesor
		if (ControllerUtils.obtenerUsuario(personaService).getRol() == RolEnum.P) {
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

		if (ControllerUtils.obtenerUsuario(personaService).getRol() == RolEnum.P) {
			PracticaDTO practicaBlanca = new PracticaDTO();

			// Añadimos un alumno en blanco
			PersonaDTO alumnoBlanco = new PersonaDTO();
			alumnoBlanco.setRol(RolEnum.E);
			alumnoBlanco.setUbicacion(new UbicacionDTO());
			alumnoBlanco.setOrganizacion(ControllerUtils.obtenerUsuario(personaService).getOrganizacion());
			practicaBlanca.setAlumno(alumnoBlanco);

			// Añadimos tutor
			practicaBlanca.setTutor(ControllerUtils.obtenerUsuario(personaService));

			// Añadimos Centro
			practicaBlanca.setCentro(ControllerUtils.obtenerUsuario(personaService).getOrganizacion());

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
		if (ControllerUtils.obtenerUsuario(personaService).getRol() != RolEnum.P) {
			// TODO Mostar error de permisos
			return ControllerUtils.mostarError(0, model);
		}

		String errorCampos = comprobarCampos(practicaForm, model);
		if (errorCampos != null)
			return errorCampos;

		// Comprobar si el numero de la seguridad social está duplicado
		if (practicaService.existsBynumeroSeguridadSocial(practicaForm.getNumeroSeguridadSocial())) {
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
		practicaForm.setTutor(ControllerUtils.obtenerUsuario(personaService));
		practicaForm.setCentro(ControllerUtils.obtenerUsuario(personaService).getOrganizacion());
		practicaForm.getAlumno().setNombre(practicaForm.getAlumno().getEmail());
		practicaForm.getAlumno().setUbicacion(ControllerUtils.obtenerUsuario(personaService).getOrganizacion().getUbicacion());
		practicaForm.getAlumno().setOrganizacion(ControllerUtils.obtenerUsuario(personaService).getOrganizacion());
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

		practicaService.save(practicaForm);

		return mostrarTabla(model);
	}

	@GetMapping("/form/{id}/{tipo}")
	public String form(@PathVariable("id") Long id, @PathVariable("tipo") String tipo, Model model) {

		if (id == null) {
			// TODO Error id de la practica no valida
			return ControllerUtils.mostarError(0, model);
		}

		if (!"D".equals(tipo) && !"E".equals(tipo)) {
			// TODO tipo de formulario no valido
			return ControllerUtils.mostarError(0, model);
		}

		PracticaDTO form = practicaService.findById(id);

		if (form == null) {
			// TODO Error practica no encontrada en la base de datos
			return ControllerUtils.mostarError(0, model);
		}

		return mostrarFormulario(form, model, tipo);

	}

	private String comprobarCampos(PracticaDTO practica, Model model) {

		// Comrobar si hay datos obligatorios nulos
		if (practica == null || practica.getDuracion() < 0 || practica.getInicio() == null
				|| practica.getFin() == null) {
			// TODO Mostrar error campos obligatorios vacios
			return ControllerUtils.mostarError(0, model);
		}

		// Comprobar que la fecha de inicio y fin son correctas
		if (practica.getFin().isBefore(practica.getInicio())) {
			// TODO Mostrar error la fecha de inicio es postarior a la de fin
			return ControllerUtils.mostarError(0, model);
		}

		return null;
	}

	@PostMapping("/editarDatos")
	public String editarDatos(@ModelAttribute("practicaForm") PracticaDTO practicaForm, Model model) {

		if (practicaForm.getId() == null) {
			// TODO Error id de la practica no valida
			return ControllerUtils.mostarError(0, model);
		}

		if (ControllerUtils.obtenerUsuario(personaService).getRol() != RolEnum.P) {
			// TODO Error el usuario no tiene permiso para modificar una practica
			return ControllerUtils.mostarError(0, model);
		}

		PracticaDTO practicaDB = practicaService.findById(practicaForm.getId());

		if (practicaDB == null) {
			// TODO Error la practica no existe en la base de datos
			return ControllerUtils.mostarError(0, model);
		}

		// Comprobar que campos se ha modificado
		if (practicaForm.getDuracion() > 0 && practicaDB.getDuracion() != practicaForm.getDuracion())
			practicaDB.setDuracion(practicaForm.getDuracion());

		if (practicaForm.getInicio() != null && !practicaForm.getInicio().equals(practicaDB.getInicio()))
			practicaDB.setInicio(practicaForm.getInicio());

		if (practicaForm.getFin() != null && !practicaForm.getFin().equals(practicaDB.getFin()))
			practicaDB.setFin(practicaForm.getFin());

		if (!practicaForm.getNumeroSeguridadSocial().equals(practicaDB.getNumeroSeguridadSocial()) && !practicaService
				.existsByNumeroSeguridadSocialAndIdNot(practicaForm.getNumeroSeguridadSocial(), practicaForm.getId())) {
			practicaDB.setNumeroSeguridadSocial(practicaForm.getNumeroSeguridadSocial());
		}

		String errorCampos = comprobarCampos(practicaDB, model);
		if (errorCampos != null)
			return null;

		practicaService.save(practicaDB);

		return mostrarTabla(model);
	}

	@PostMapping("/editarEmpresa")
	public String editarEmpresa(@ModelAttribute("practicaForm") PracticaDTO practicaForm, Model model) {

		PracticaDTO practicaDB = practicaService.findById(practicaForm.getId());

		if (practicaDB == null) {
			// TODO mostrar error practica no encontrada en la base de datos
			ControllerUtils.mostarError(0, model);
		}

		// Datos que nunca cambian
		practicaForm.setAlumno(practicaDB.getAlumno());
		practicaForm.setTutor(practicaDB.getTutor());
		practicaForm.setCentro(practicaDB.getCentro());
		practicaForm.getResponsable().setRol(RolEnum.R);
		practicaForm.getEmpresa().setTipo(TipoEnum.E);

		// No cambia el responsable
		if (practicaForm.getResponsable().getId() == practicaDB.getResponsable().getId()) {

			practicaForm.getResponsable().setId((practicaDB.getResponsable().getId()));

			practicaDB.setResponsable(practicaForm.getResponsable());

			// No cambia la empresa
			if (practicaForm.getEmpresa().getId() == practicaDB.getEmpresa().getId()) {

				practicaForm.getEmpresa().setId(practicaDB.getEmpresa().getId());
				practicaForm.getResponsable().setOrganizacion(practicaDB.getEmpresa());

				practicaDB.setEmpresa(practicaForm.getEmpresa());

			} else {

				OrganizacionDTO empresa = organizacionService.findById(practicaForm.getEmpresa().getId());

				// Empresa nueva
				if (empresa == null) {
					practicaDB.setEmpresa(practicaForm.getEmpresa());
					practicaForm.getResponsable().setOrganizacion(practicaForm.getEmpresa());
				} else {
					// Otra empresa en la bbdd
					practicaDB.setEmpresa(empresa);
					practicaDB.getResponsable().setOrganizacion(empresa);
				}
			}

		} else {

			PersonaDTO respon = personaService.findById(practicaForm.getResponsable().getId());

			// Responsable nuevo
			if (respon == null) {
				practicaDB.setResponsable(practicaForm.getResponsable());
				practicaDB.getResponsable().setOrganizacion(practicaForm.getEmpresa());
				practicaDB.setEmpresa(practicaForm.getEmpresa());
			} else {
				// Otro responsable en la bbdd
				practicaDB.setResponsable(respon);
				practicaDB.setEmpresa(respon.getOrganizacion());
			}
		}

		practicaService.save(practicaDB);

		return mostarDetalle(practicaDB, model);

	}
}
