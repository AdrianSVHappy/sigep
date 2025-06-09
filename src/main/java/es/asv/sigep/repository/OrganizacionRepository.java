package es.asv.sigep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.asv.sigep.entities.OrganizacionEntity;
import es.asv.sigep.enums.TipoEnum;

@Repository
public interface OrganizacionRepository extends JpaRepository<OrganizacionEntity, Long>{

	List<OrganizacionEntity> findAllByTipo(TipoEnum t);

}
