package es.asv.sigep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.asv.sigep.entities.MensajeEntity;
import es.asv.sigep.entities.PersonaEntity;

@Repository
public interface MensajeRepository  extends JpaRepository<MensajeEntity, Long>{

	List<MensajeEntity> findAllByAutorOrReceptorOrderByFechaDesc(PersonaEntity autor, PersonaEntity receptor);
	
}
