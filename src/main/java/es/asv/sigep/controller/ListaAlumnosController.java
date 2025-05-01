package es.asv.sigep.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.asv.sigep.entities.OrganizacionEntity;
import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.entities.UbicacionEntity;
import es.asv.sigep.enums.RolEnum;

@Controller
public class ListaAlumnosController {

	@GetMapping("/alumnos")
	public String inicio(Model model) {

		ControllerUtils.modelFooter(model);
		return "listaAlumnos";
	}

}
