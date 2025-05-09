package es.asv.sigep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.asv.sigep.entities.OrganizacionEntity;

@Repository
public interface OrganizacionRepository extends JpaRepository<OrganizacionEntity, Long>{

}
