package es.asv.sigep.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.asv.sigep.entities.PracticaEntity;

@Controller
public class ListaPracticasController {

	
	@GetMapping("/practicas")
	public String inicio(Model model) {
		
		
		model.addAttribute("practicas", ControllerUtils.generarPracticasFake(ControllerUtils.generarAlumnosFake()));
		
		ControllerUtils.modelFooter(model);
		
		return "listaPracticas";
		
	}
	
	
	
	private List<PracticaEntity> genPrueba(){
		
		ArrayList<PracticaEntity> ret = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			PracticaEntity aux = new PracticaEntity();
			
			ret.add(aux);
		}
		
		return ret;
		
	}
}
