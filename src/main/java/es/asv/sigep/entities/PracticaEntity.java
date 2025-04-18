package es.asv.sigep.entities;

import java.util.Date;

import lombok.Data;

@Data
public class PracticaEntity {

	private Long identificador;
	
	private PersonaEntity alumno;
	
	private PersonaEntity tutor;
	
	private PersonaEntity responsable;
	
	private OrganizacionEntity centro;
	
	private OrganizacionEntity empresa;
	
	private int duracion;
	
	private Date inicio;
	
	private Date fin;
	
	private String numeroSeguridadSocial;
	
}
