package es.asv.sigep.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "REGISTROS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@JoinColumn(name = "PRACTICA", referencedColumnName = "ID", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private PracticaEntity practica;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "FECHA", nullable = false)
	private LocalDate fecha;
	
	@Column(name = "DIA", length = 2)
	private Integer dia;
	
	@Column(name = "MES", length = 2)
	private Integer mes;
	
	@Column(name = "ANIO")
	private Integer anio;
	
	@DateTimeFormat(pattern = "HH:mm")
	@Column(name = "INICIO")
	private LocalTime horaInicio;
	
	@DateTimeFormat(pattern = "HH:mm")
	@Column(name = "FIN")
	private LocalTime horaFin;
	
	@DateTimeFormat(pattern = "HH:mm")
	@Column(name = "INICIO2")
	private LocalTime horaInicio2;
	
	@DateTimeFormat(pattern = "HH:mm")
	@Column(name = "FIN2")
	private LocalTime horaFin2;
	
	@Column(name = "TEXTO", length = 200)
	private String texto;
	
	@Column(name = "REGISTRABLE")
	private boolean registrable;
	
	@Column(name = "REGISTRADO")
	private boolean registrado;
	
}
