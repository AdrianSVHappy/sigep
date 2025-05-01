package es.asv.sigep.dto;

import es.asv.sigep.enums.TipoEnum;
import lombok.Data;

@Data
public class OrganizacionDTO {

	private Long id;
	
	private String nombre;
	
	private UbicacionDTO ubicacion;
	
	private TipoEnum tipo;
}
