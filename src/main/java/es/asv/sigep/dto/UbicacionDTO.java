package es.asv.sigep.dto;


import lombok.Data;

@Data
public class UbicacionDTO {

	private Long id;
	
	private String pais;
	
	private String comunidad;

	private String provincia;
 
	private String ciudad;
 
	private String codigoPostal;

	private String direccion;
	
}
