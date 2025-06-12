package es.asv.sigep.dto;

import es.asv.sigep.enums.RolEnum;

import lombok.Data;

@Data
public class PersonaDTO {

	private Long id;

	private String nombre;

	private String apellidos;

	private OrganizacionDTO organizacion;

	private UbicacionDTO ubicacion;

	private String imagenPerfil;

	private RolEnum rol;

	private String email;

	private String telefono;

}
