package es.asv.sigep.entities;

import es.asv.sigep.enums.RolEnum;
import lombok.Data;
import lombok.Setter;


@Data
public class PersonaEntity {

	private Long id;
	
	private String nombre;
	
	private String apellidos;
	
	private OrganizacionEntity organizacion;
	
	private UbicacionEntity ubicacion;
	
	private String imagenPerfil;
	
	private RolEnum rol;


}
