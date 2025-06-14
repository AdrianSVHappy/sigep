package es.asv.sigep.entities;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import es.asv.sigep.dto.PersonaDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MENSAJES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@JoinColumn(name = "AUTOR", referencedColumnName = "ID", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private PersonaEntity autor;

	@JoinColumn(name = "RECEPTOR", referencedColumnName = "ID", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private PersonaEntity receptor;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "FECHA", nullable = false)
	private LocalDateTime fecha;

	@Column(name = "ASUNTO", length = 50)
	private String asunto;

	@Column(name = "TEXTO", length = 200)
	private String texto;

}
