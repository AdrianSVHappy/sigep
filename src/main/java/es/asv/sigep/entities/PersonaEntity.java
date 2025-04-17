package es.asv.sigep.entities;

import es.asv.sigep.enums.RolEnum;

public class PersonaEntity {

	private Long id;
	
	private String nombre;
	
	private String apellidos;
	
	private OrganizacionEntity organizacion;
	
	private UbicacionEntity ubicacion;
	
	private RolEnum rol;
}
