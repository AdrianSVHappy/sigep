package es.asv.sigep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.asv.sigep.dto.OrganizacionDTO;
import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.dto.PracticaDTO;
import es.asv.sigep.dto.UbicacionDTO;
import es.asv.sigep.enums.RolEnum;
import es.asv.sigep.enums.TipoEnum;
import es.asv.sigep.service.OrganizacionService;
import es.asv.sigep.service.PersonaService;
import es.asv.sigep.service.PracticaService;
import es.asv.sigep.service.UbicacionService;

@Controller
@RequestMapping("/practica")
public class PracticasController {

	@Autowired
	private PracticaService practicaService;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private OrganizacionService organizacionService;

	@Autowired
	private UbicacionService ubicacionService;

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
			practica = practicaService.findByAlumno(ControllerUtils.obtenerUsuario(personaService));
		}

		// Profesor
		if (ControllerUtils.obtenerUsuario(personaService).getRol() == RolEnum.P) {
			practica = practicaService.findById(id);
		}

		// Practica no encontrada
		if (practica != null) {
			return mostarDetalle(practica, model);
		}

		// Error de permiso
		return ControllerUtils.mostarError(-1, personaService, model);

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

		// Error de permiso
		return ControllerUtils.mostarError(-1, personaService, model);

	}

	@PostMapping("/guardar")
	public String guardar(@ModelAttribute("practicaForm") PracticaDTO practicaForm, Model model) {

		String errorCampos = comprobarCampos(practicaForm, model);
		if (errorCampos != null)
			return errorCampos;

		// Comprobar si el numero de la seguridad social está duplicado
		if (practicaService.existsBynumeroSeguridadSocial(practicaForm.getNumeroSeguridadSocial())) {
			return ControllerUtils.mostarError(6, personaService, model);
		}

		// Establecer responsable predefinido
		if (practicaForm.getResponsable().getId() != null) {

			PersonaDTO tutorDB = personaService.findById(practicaForm.getResponsable().getId());

			if (tutorDB == null) {
				return ControllerUtils.mostarError(3, personaService, model);
			}

			practicaForm.setResponsable(tutorDB);
			practicaForm.setEmpresa(tutorDB.getOrganizacion());

		}

		// Establecer empresa predefinida
		else if (practicaForm.getEmpresa().getId() != null) {

			OrganizacionDTO empresaDB = organizacionService.findById(practicaForm.getEmpresa().getId());

			if (empresaDB == null) {
				return ControllerUtils.mostarError(3, personaService, model);
			}

			practicaForm.setEmpresa(empresaDB);
			practicaForm.getResponsable().setUbicacion(empresaDB.getUbicacion());

		}

		// Establecer datos constantes
		practicaForm.setTutor(ControllerUtils.obtenerUsuario(personaService));
		practicaForm.setCentro(ControllerUtils.obtenerUsuario(personaService).getOrganizacion());
		practicaForm.getAlumno().setNombre(practicaForm.getAlumno().getEmail());
		UbicacionDTO nuevaUbi = ControllerUtils.obtenerUsuario(personaService).getOrganizacion().getUbicacion();
		nuevaUbi.setId(null);
		practicaForm.getAlumno().setUbicacion(nuevaUbi);
		practicaForm.getAlumno().setOrganizacion(ControllerUtils.obtenerUsuario(personaService).getOrganizacion());
		practicaForm.getAlumno().setRol(RolEnum.E);
		practicaForm.getAlumno().setApellidos(" ");

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

		if (!"D".equals(tipo) && !"E".equals(tipo)) {
			return ControllerUtils.mostarError(-1, personaService, model);
		}

		PracticaDTO form = practicaService.findById(id);

		if (form == null) {
			return ControllerUtils.mostarError(3, personaService, model);
		}

		return mostrarFormulario(form, model, tipo);

	}

	private String comprobarCampos(PracticaDTO practica, Model model) {

		// Comrobar si hay datos obligatorios nulos
		if (practica == null || practica.getDuracion() < 0 || practica.getInicio() == null
				|| practica.getFin() == null) {
			return ControllerUtils.mostarError(2, personaService, model);
		}

		// Comprobar que la fecha de inicio y fin son correctas
		if (practica.getFin().isBefore(practica.getInicio())) {
			return ControllerUtils.mostarError(7, personaService, model);
		}

		if(personaService.existsByEmail(practica.getAlumno().getEmail()) || personaService.existsByEmail(practica.getResponsable().getEmail())) {
			return ControllerUtils.mostarError(8, personaService, model);
		}
		
		return null;
	}

	@PostMapping("/editarDatos")
	public String editarDatos(@ModelAttribute("practicaForm") PracticaDTO practicaForm, Model model) {

		PracticaDTO practicaDB = practicaService.findById(practicaForm.getId());

		if (practicaDB == null) {
			return ControllerUtils.mostarError(3, personaService, model);
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
			return ControllerUtils.mostarError(3, personaService, model);
		}

		// Datos que nunca cambian
		practicaForm.setAlumno(practicaDB.getAlumno());
		practicaForm.setTutor(practicaDB.getTutor());
		practicaForm.setCentro(practicaDB.getCentro());
		practicaForm.getResponsable().setRol(RolEnum.R);
		practicaForm.getEmpresa().setTipo(TipoEnum.E);

		// No cambia el responsable
		if (practicaDB.getResponsable().getId().equals(practicaForm.getResponsable().getId())) {

			practicaForm.getResponsable().setId((practicaDB.getResponsable().getId()));

			practicaDB.setResponsable(practicaForm.getResponsable());

			// No cambia la empresa
			if (practicaDB.getEmpresa().getId().equals(practicaForm.getEmpresa().getId())) {

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
