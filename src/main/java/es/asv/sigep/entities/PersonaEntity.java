package es.asv.sigep.entities;

import es.asv.sigep.converter.enums.RolEnumConvert;
import es.asv.sigep.enums.RolEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
@Table(name = "PERSONAS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NOMBRE", nullable = false)
	private String nombre;

	@Column(name = "APELLIDOS")
	private String apellidos;

	@JoinColumn(name = "ORGANIZACION", referencedColumnName = "ID", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private OrganizacionEntity organizacion;

	@JoinColumn(name = "UBICACION", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private UbicacionEntity ubicacion;

	@Column(name = "FOTO")
	private String imagenPerfil;

	@Column(name = "ROL", nullable = false)
	@Convert(converter = RolEnumConvert.class)
	private RolEnum rol;

	@Column(name = "EMAIL", nullable = false)
	private String email;

	@Column(name = "TELEFONO")
	private String telefono;

}
