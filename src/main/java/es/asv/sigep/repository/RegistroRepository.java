package es.asv.sigep.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.asv.sigep.entities.PracticaEntity;
import es.asv.sigep.entities.RegistroEntity;

@Repository
public interface RegistroRepository extends JpaRepository<RegistroEntity, Long>{

	boolean existsByPracticaAndFecha(PracticaEntity practica, LocalDate fecha);
	
	List<RegistroEntity> findAllByPractica(PracticaEntity practica);
	
	Optional<RegistroEntity> findByPracticaAndFecha(PracticaEntity practica, LocalDate fecha);
}
