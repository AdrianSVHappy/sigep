package es.asv.sigep.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "UBICACIONES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UbicacionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "PAIS")
	String pais;

	@Column(name = "COMUNIDAD")
	String comunidad;

	@Column(name = "PROVINCIA")
	String provincia;

	@Column(name = "CIUDAD")
	String ciudad;

	@Column(name = "CODIGO_POSTAL")
	String codigoPostal;

	@Column(name = "DIRECCION")
	String direccion;
}
