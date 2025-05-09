package es.asv.sigep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.entities.PracticaEntity;

@Repository
public interface PracticaRepository extends JpaRepository<PracticaEntity, Long>{

	 List<PracticaEntity> findAllByTutor(PersonaEntity tutor);
	

}
