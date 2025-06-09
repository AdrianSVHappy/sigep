package es.asv.sigep.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.enums.RolEnum;

@Repository
public interface PersonaRepository extends JpaRepository<PersonaEntity, Long>{

	/**
	 * Busca a una persona por su id
	 */
	Optional<PersonaEntity> findById(Long id);

	List<PersonaEntity> findAllByRol(RolEnum r);
	
}
