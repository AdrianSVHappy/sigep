package es.asv.sigep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.asv.sigep.entities.PermisoEntity;
import es.asv.sigep.entities.PermisoId;

public interface PermisoRepository extends JpaRepository<PermisoEntity, PermisoId>{

}
