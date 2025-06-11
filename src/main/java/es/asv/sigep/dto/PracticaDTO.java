package es.asv.sigep.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PracticaDTO {

	private Long id;

	private PersonaDTO alumno;

	private PersonaDTO tutor;

	private PersonaDTO responsable;

	private OrganizacionDTO centro;

	private OrganizacionDTO empresa;

	private int duracion;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate inicio;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate fin;

	private String numeroSeguridadSocial;
}
