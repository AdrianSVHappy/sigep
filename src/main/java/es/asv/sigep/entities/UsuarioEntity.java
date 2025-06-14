package es.asv.sigep.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioEntity {
	
	@Id
	@Column(name = "username", nullable = false,  length = 50)
	private String user;
	
	@Column(name = "password", nullable = false,  length = 250)
	private String passw;
	
	@Column(name = "enabled", nullable = false)
	private boolean activo;
}
