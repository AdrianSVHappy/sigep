package es.asv.sigep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.asv.sigep.entities.UbicacionEntity;

@Repository
public interface UbicacionRepository extends JpaRepository<UbicacionEntity, Long> {

}
