package es.asv.sigep.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.asv.sigep.entities.PersonaEntity;

@Repository
public interface PersonaRepository extends JpaRepository<PersonaEntity, Long>{

	/**
	 * Busca a una persona por su id
	 */
	Optional<PersonaEntity> findById(Long id);
	
}
