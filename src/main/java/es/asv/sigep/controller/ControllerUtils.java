package es.asv.sigep.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.service.PersonaService;

@Controller
public class ControllerUtils {

	@Autowired
	private PersonaService personaService;

	public static void modelFooter(Model model) {

		String email = "adriansuarezvaldayo573@gmail.com";
		String telef = "644 63 44 62";
		String direc = "C\\ Dalí Nº 9";

		model.addAttribute("email", email);
		model.addAttribute("telef", telef);
		model.addAttribute("direc", direc);

	}

	public static String mostarError(int error, PersonaService ser , Model model) {

		model.addAttribute("codigo", error);
		String msj = "Ops, algo salió mal....";
		
		switch (error) {
		case 0:
			msj =  "El formulario no se completó correctamente";
			break;
		case 1:
			msj =  "Las contraseñas no coinciden";
			break;
		case 2:
			msj = "Hay campos vacíos en el formulario";
			break;
		case 3:
			msj = "No existe en la base de datos";
			break;
		case 4:
			msj = "No se puede guardar";
			break;
		case 5:
			msj = "El orden de las horas es invalido";
			break;
		case 6:
			msj = "El número de la seguridad social no se puede repetir";
			break;
		case 7:
			msj = "El orden de las fechas es invalido";
			break;
		case 8:
			msj = "El email no se puede repetir";
		default:
			
		}
		
		modelPersona(ser, model);
		modelFooter(model);
		model.addAttribute("mensaje", msj);
		return "error";

	}

	public static String traducirMes(int codMes) {

		ArrayList<String> meses = new ArrayList<>();

		meses.add("Enero");
		meses.add("Febrero");
		meses.add("Marzo");
		meses.add("Abril");
		meses.add("Mayo");
		meses.add("Junio");
		meses.add("Julio");
		meses.add("Agosto");
		meses.add("Septiembre");
		meses.add("Octubre");
		meses.add("Noviembre");
		meses.add("Diciembre");

		return meses.get(codMes - 1);
	}

	public static void modelPersona(PersonaService ser, Model model) {

		model.addAttribute("persona", obtenerUsuario(ser));
	}

	public static PersonaDTO obtenerUsuario(PersonaService ser) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		return ser.findByEmail(auth.getName());
	}

}
