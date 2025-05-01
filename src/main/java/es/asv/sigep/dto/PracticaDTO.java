package es.asv.sigep.dto;

import java.util.Date;

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

	private Date inicio;

	private Date fin;

	private String numeroSeguridadSocial;
}
 