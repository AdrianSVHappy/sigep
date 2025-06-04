package es.asv.sigep.dto;

import java.time.LocalDate;

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

	private LocalDate inicio;

	private LocalDate fin;

	private String numeroSeguridadSocial;
}
 