package es.asv.sigep.entities;

import es.asv.sigep.enums.RolEnum;
import es.asv.sigep.enums.TipoEnum;
import lombok.Data;

@Data
public class OrganizacionEntity {

	private Long id;
	
	private String nombre;
	
	private UbicacionEntity ubicacion;
	
	private TipoEnum tipo;
	
	
}
