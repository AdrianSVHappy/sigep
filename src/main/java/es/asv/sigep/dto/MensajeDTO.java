package es.asv.sigep.dto;


import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MensajeDTO {

	private Long id;
	
	private PersonaDTO autor;

	private PersonaDTO receptor;

	private LocalDateTime fecha;

	private String asunto;

	private String texto;
	
}
