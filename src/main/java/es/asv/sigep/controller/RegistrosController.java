package es.asv.sigep.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.asv.sigep.dto.PracticaDTO;
import es.asv.sigep.dto.RegistroDTO;
import es.asv.sigep.service.PersonaService;
import es.asv.sigep.service.PracticaService;
import es.asv.sigep.service.RegistroService;

@Controller
@RequestMapping("/registro")
public class RegistrosController {

	@Autowired
	private PracticaService practicaService;

	@Autowired
	private RegistroService registroService;

	@Autowired
	private PersonaService personaService;

	/**
	 * Muesta la pantaslla de calendario
	 * 
	 * @param model
	 * @return vista
	 */
	private String mostrarCalendario(Model model) {

		ControllerUtils.modelFooter(model);
		ControllerUtils.modelPersona(personaService, model);

		return "registro/calendario";
	}

	private String mostarFecha(RegistroDTO registro, Model model) {

		model.addAttribute("registro", registro);
		ControllerUtils.modelFooter(model);
		ControllerUtils.modelPersona(personaService, model);

		return "registro/fecha";
	}

	private String mostrarTabla(List<RegistroDTO> registros, Long practicaId, Model model) {

		model.addAttribute("registros", registros);
		model.addAttribute("practicaId", practicaId);
		ControllerUtils.modelPersona(personaService, model);
		ControllerUtils.modelFooter(model);

		return "registro/tablaRegistros";
	}

	@GetMapping("calendario")
	public String inicio(Model model) {

		// Practicas el alumno
		PracticaDTO practica = practicaService.findByAlumno(ControllerUtils.obtenerUsuario(personaService));

		if (practica != null) {

			empaquetarCalendario(practica, model);

			model.addAttribute("practica", practica);
		}

		return mostrarCalendario(model);
	}

	private void empaquetarCalendario(PracticaDTO practica, Model model) {

		// Calendario
		HashMap<Integer, List<RegistroDTO[]>> calendario = new HashMap<>();

		// Ultimo dia de las practicas
		LocalDate fin = practica.getFin();

		LocalDate fecha = practica.getInicio();

		RegistroDTO[] semana = new RegistroDTO[7];

		// Dias registrados
		List<RegistroDTO> diasRegistrados = registroService.findAllByPracticaOrderByFechaAsc(practica.getId());

		// Horas totales consumidas
		float consumido = 0;

		while (!fecha.isAfter(fin)) {
			// Dia de la semana
			int indice = fecha.getDayOfWeek().getValue();

			// Mes del año
			int mesId = fecha.getMonthValue();

			// Registro del dia
			RegistroDTO dia = new RegistroDTO();
			dia.setFecha(fecha);
			dia.setRegistrable(indice < 6);

			for (RegistroDTO reg : diasRegistrados) {
				if (fecha.equals(reg.getFecha())) {
					dia.setRegistrado(true);
					consumido += calcularTiempoRegistrado(reg);
					break;
				}
			}

			// Guardamos el dia en la semana
			semana[indice - 1] = dia;

			// Comprobar si se ha terminado la semana
			if (indice == 7 || fecha.lengthOfMonth() == fecha.getDayOfMonth() || fecha.equals(fin)) {

				RegistroDTO[] semanaCopia = Arrays.copyOf(semana, 7);

				// Si no existe el mes se crea vacio
				if (calendario.get(mesId) == null)
					calendario.put(mesId, new ArrayList<>());

				calendario.get(mesId).add(semanaCopia);

				semana = new RegistroDTO[7];
			}

			fecha = fecha.plusDays(1);

		}

		model.addAttribute("consumido", consumido);
		model.addAttribute("hoy", LocalDate.now());
		model.addAttribute("calendario", calendario);
	}

	/**
	 * Calcula el tiempo consumido en un registro
	 * 
	 * @param registro
	 * @return float Las horas consumidas
	 */
	private float calcularTiempoRegistrado(RegistroDTO registro) {

		float ret = 0;
		long minutos = 0;

		if (registro != null) {

			if (registro.getHoraInicio() != null && registro.getHoraFin() != null) {

				minutos = ChronoUnit.MINUTES.between(registro.getHoraInicio(), registro.getHoraFin());
				ret = minutos / 60f;

			}

			if (registro.getHoraInicio2() != null && registro.getHoraFin2() != null) {

				minutos = ChronoUnit.MINUTES.between(registro.getHoraInicio2(), registro.getHoraFin2());
				ret += minutos / 60f;
			}
		}

		return ret;
	}

	@GetMapping("/fecha/{fecha}")
	public String fecha(@PathVariable("fecha") LocalDate fecha, Model model) {

		// Practicas el alumno
		PracticaDTO practica = practicaService.findByAlumno(ControllerUtils.obtenerUsuario(personaService));

		if (practica != null && fecha != null) {

			// Registro segun la fecha
			RegistroDTO registro = registroService.findByPracticaAndFecha(practica.getId(), fecha);

			if (registro == null) {
				// registro en blanco
				registro = new RegistroDTO();
				registro.setFecha(fecha);
				registro.setRegistrable(true);
			} else {
				System.out.println(registro);
			}

			// Comprobar si la fecha es valida
			if (estaEntre(practica.getInicio(), practica.getFin(), fecha))
				return mostarFecha(registro, model);

		}

		// Error de permiso
		return ControllerUtils.mostarError(-1, personaService, model);

	}

	private boolean estaEntre(LocalDate inicio, LocalDate fin, LocalDate actual) {
		return (actual.isEqual(inicio) || actual.isAfter(inicio)) && (actual.isEqual(fin) || actual.isBefore(fin));
	}

	@PostMapping("/guardar")
	public String guardar(@ModelAttribute("registro") RegistroDTO registro, Model model) {

		// Error si no se pasa un registro
		if (registro == null)
			return ControllerUtils.mostarError(0, personaService, model);

		if (!registro.isRegistrable()) {
			return ControllerUtils.mostarError(4, personaService, model);
		}

		if (registro.getHoraInicio() == null) {
			return ControllerUtils.mostarError(2, personaService, model);
		}

		if ((registro.getHoraInicio() != null && registro.getHoraFin() != null
				&& registro.getHoraInicio().isAfter(registro.getHoraFin()))
				|| (registro.getHoraInicio2() != null && registro.getHoraFin2() != null
						&& registro.getHoraInicio2().isAfter(registro.getHoraFin2()))) {
			return ControllerUtils.mostarError(5, personaService, model);
		}

		// Ponemos la practica del usuario loqueado
		PracticaDTO practica = practicaService.findByAlumno(ControllerUtils.obtenerUsuario(personaService));

		// Guardamo la practica completa en el registro
		registro.setPractica(practica);

		// Marcamos el dia como registrado
		registro.setRegistrado(true);

		// guardamos el registro
		registroService.save(registro);

		// Todo ha salido bien
		return inicio(model);
	}

	@GetMapping("/registros/{id}")
	public String mostrarRegistros(@PathVariable("id") Long id, Model model) {

		if (!practicaService.existsById(id)) {
			return ControllerUtils.mostarError(3, personaService, model);
		}

		List<RegistroDTO> registros = registroService.findAllByPracticaOrderByFechaAsc(id);

		return mostrarTabla(registros, id, model);
	}

}
