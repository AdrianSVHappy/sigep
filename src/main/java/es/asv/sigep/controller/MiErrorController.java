package es.asv.sigep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.asv.sigep.service.PersonaService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MiErrorController {

	@Autowired
	private PersonaService personaService;

	@RequestMapping(value = "/error")
	public String handleError(HttpServletRequest request, Model model) {

		Object statusObj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		Object errorObj = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
		Object exceptionObj = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		Object pathObj = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

		int status = statusObj != null ? Integer.parseInt(statusObj.toString()) : 0;
		String error = errorObj != null ? errorObj.toString() : "";
		String exception = exceptionObj != null ? exceptionObj.toString() : "";
		String path = pathObj != null ? pathObj.toString() : "";

		// atributos de error
		model.addAttribute("status", status);
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		model.addAttribute("path", path);

		ControllerUtils.modelPersona(personaService, model);

		return "error";
	}

}
