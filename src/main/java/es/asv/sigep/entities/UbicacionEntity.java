package es.asv.sigep.entities;

import es.asv.sigep.enums.RolEnum;
import lombok.Data;

@Data
public class UbicacionEntity {

	String pais;
	
	String comunidad;
	
	String provincia;
	
	String ciudad;
	
	String codigoPostal;
	
	String direccion;
	
}
