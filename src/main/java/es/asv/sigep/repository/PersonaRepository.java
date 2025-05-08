package es.asv.sigep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.asv.sigep.entities.PersonaEntity;

public interface PersonaRepository extends JpaRepository<PersonaEntity, Long>{

}
