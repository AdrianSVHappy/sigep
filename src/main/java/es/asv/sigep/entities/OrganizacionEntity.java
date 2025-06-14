package es.asv.sigep.entities;

import es.asv.sigep.converter.enums.TipoEnumConvert;
import es.asv.sigep.enums.TipoEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
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
@Table(name = "ORGANIZACIONES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizacionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NOMBRE", nullable = false, length = 50)
	private String nombre;

	@JoinColumn(name = "UBICACION", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private UbicacionEntity ubicacion;

	@Column(name = "TIPO", nullable = false)
	@Convert(converter = TipoEnumConvert.class)
	private TipoEnum tipo;

}
