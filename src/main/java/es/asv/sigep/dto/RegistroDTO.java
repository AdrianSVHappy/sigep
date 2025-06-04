package es.asv.sigep.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class RegistroDTO {

	private Long id;
	
	private PracticaDTO practica;
	
	private LocalDate fecha;
	
	private Integer dia;
	
	private Integer mes;
	
	private Integer anio;
	
	private LocalTime horaInicio;
	
	private LocalTime horaFin;
	
	private LocalTime horaInicio2;
	
	private LocalTime horaFin2;
	
	private String texto;
	
	private boolean registrable;
	
	private boolean registrado;

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
		this.dia = fecha.getDayOfMonth();
		this.mes = fecha.getMonthValue();
		this.anio = fecha.getYear();
	}
	
	@Override
	public String toString() {
		
		String ret = this.anio + ""; 
		
		if(mes < 10)
			ret += "0" + mes;
		else
			ret += mes;
		
		if(dia < 10)
			ret += "0" + dia;
		else
			ret += dia;
		
		return ret;
	}
	
	
	
} 
