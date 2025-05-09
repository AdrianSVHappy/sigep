package es.asv.sigep.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import es.asv.sigep.dto.OrganizacionDTO;
import es.asv.sigep.dto.UbicacionDTO;
import es.asv.sigep.entities.OrganizacionEntity;
import es.asv.sigep.entities.UbicacionEntity;

@Component
public class OrganizacionConverter {

	/**
	 * OrganizacionEntity -> OrganizacionDTO
	 * 
	 * @param entity OrganizacionEntity
	 * @return OrganizacionDTO
	 */
	public OrganizacionDTO convert(final OrganizacionEntity entity) {

		OrganizacionDTO dto = null;

		if (entity != null) {
			dto = new OrganizacionDTO();
			BeanUtils.copyProperties(entity, dto);

			if (entity.getUbicacion() != null) {
				UbicacionDTO ubi = new UbicacionDTO();
				BeanUtils.copyProperties(entity.getUbicacion(), ubi);
				dto.setUbicacion(ubi);
			}

		}

		return dto;
	}

	/**
	 * OrganizacionDTO -> OrganizacionEntity
	 * 
	 * @param dto OrganizacionDTO
	 * @return OrganizacionEntity
	 */
	public OrganizacionEntity convert(OrganizacionDTO dto) {

		OrganizacionEntity entity = null;
		if (dto != null) {
			entity = new OrganizacionEntity();
			BeanUtils.copyProperties(entity, dto);

			if (dto.getUbicacion() != null) {
				UbicacionEntity ubi = new UbicacionEntity();
				BeanUtils.copyProperties(ubi, dto.getUbicacion());
				entity.setUbicacion(ubi);
			}

		}

		return entity;
	}
}
