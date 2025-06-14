package es.asv.sigep.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
import es.asv.sigep.converter.UbicacionConverter;
import es.asv.sigep.dto.MensajeDTO;
import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.dto.PracticaDTO;
import es.asv.sigep.enums.RolEnum;
import es.asv.sigep.service.MensajeService;
import es.asv.sigep.service.PersonaService;
import es.asv.sigep.service.PracticaService;

@Controller
@RequestMapping("/mensaje")
public class MensajesController {

	private final UbicacionConverter ubicacionConverter;

	@Autowired
	private MensajeService mensajeService;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private PracticaService practicaService;

	MensajesController(UbicacionConverter ubicacionConverter) {
		this.ubicacionConverter = ubicacionConverter;
	}

	private String mostrarLista(Model model) {

		ControllerUtils.modelFooter(model);
		ControllerUtils.modelPersona(personaService, model);

		return "mensaje/listaMensajes";

	}

	private String mostrarFormulario(Model model) {

		ControllerUtils.modelFooter(model);
		ControllerUtils.modelPersona(personaService, model);

		return "mensaje/nuevo";
	}

	@GetMapping("/mensajes")
	public String inicio(Model model) {

		List<MensajeDTO> lista = mensajeService
				.findAllByAutorOrReceptorOrderByFechaDesc(ControllerUtils.obtenerUsuario(personaService));

		model.addAttribute("listaMensajes", lista);
		ControllerUtils.modelPersona(personaService, model);

		return mostrarLista(model);

	}

	@GetMapping("/nuevo")
	public String nuevoMensaje(Model model) {

		if (ControllerUtils.obtenerUsuario(personaService).getRol() == RolEnum.P) {
			List<PracticaDTO> practicas = practicaService.findAllByTutor(ControllerUtils.obtenerUsuario(personaService));
			List<PersonaDTO> alumnos = new ArrayList<>();

			for (PracticaDTO practica : practicas) {

				alumnos.add(practica.getAlumno());
			}

			model.addAttribute("receptores", alumnos);

		}

		model.addAttribute("mensaje", new MensajeDTO());

		return mostrarFormulario(model);
	}

	@PostMapping("/enviar")
	public String enviarMensaje(@ModelAttribute("mensaje") MensajeDTO mensaje, Model model) {

		if ((ControllerUtils.obtenerUsuario(personaService).getRol() == RolEnum.P) && (mensaje.getReceptor().getId() != -1)
				&& (!practicaService.existsByTutorAndAlumno(ControllerUtils.obtenerUsuario(personaService), mensaje.getReceptor()))) {
			return ControllerUtils.mostarError(3, personaService , model);

		}

		// Establecemos los datos estaticos
		mensaje.setAutor(ControllerUtils.obtenerUsuario(personaService));
		mensaje.setFecha(LocalDateTime.now());

		if (mensaje.getReceptor() == null || mensaje.getReceptor().getId() != -1) {
			// Establecer datos del receptor para profesor
			if (ControllerUtils.obtenerUsuario(personaService).getRol() == RolEnum.P)
				mensaje.setReceptor(personaService.findById(mensaje.getReceptor().getId()));

			// Establecer datos del receptor para alumno
			if (ControllerUtils.obtenerUsuario(personaService).getRol() == RolEnum.E)
				mensaje.setReceptor(practicaService.findByAlumno(ControllerUtils.obtenerUsuario(personaService)).getTutor());

			mensajeService.save(mensaje);

		} else {
			// Se envia mensajes a todos los alumnos
			List<PracticaDTO> practicas = practicaService.findAllByTutor(ControllerUtils.obtenerUsuario(personaService));

			for (PracticaDTO practica : practicas) {
				mensaje.setReceptor(practica.getAlumno());
				mensajeService.save(mensaje);
			}

		}

		return inicio(model);
	}

}
