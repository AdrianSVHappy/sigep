package es.asv.sigep.entities;

import java.util.Date;

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
@Table(name = "PRACTICAS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PracticaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@JoinColumn(name = "ALUMNO", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private PersonaEntity alumno;

	@JoinColumn(name = "TUTOR", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private PersonaEntity tutor;

	@JoinColumn(name = "RESPONSABLE", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private PersonaEntity responsable;

	@JoinColumn(name = "CENTRO", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private OrganizacionEntity centro;

	@JoinColumn(name = "EMPRESA", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private OrganizacionEntity empresa;

	@Column(name = "DURACION")
	private int duracion;

	@Column(name = "INICIO")
	private Date inicio;

	@Column(name = "FIN")
	private Date fin;

	@Column(name = "SEGURIDADSOCIAL")
	private String numeroSeguridadSocial;

}
