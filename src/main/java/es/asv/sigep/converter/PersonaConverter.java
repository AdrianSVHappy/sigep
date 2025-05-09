package es.asv.sigep.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import es.asv.sigep.dto.OrganizacionDTO;
import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.dto.UbicacionDTO;
import es.asv.sigep.entities.OrganizacionEntity;
import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.entities.UbicacionEntity;

@Component
public class PersonaConverter {

	/**
	 * PersonaEntity -> PersonaDTO
	 * 
	 * @param entity PersonaEntity
	 * @return PersonaDTO
	 */
	public PersonaDTO convert(PersonaEntity entity) {

		PersonaDTO dto = null;
		if (entity != null) {
			dto = new PersonaDTO();
			BeanUtils.copyProperties(entity, dto);

			if (entity.getOrganizacion() != null) {
				OrganizacionDTO org = new OrganizacionDTO();
				BeanUtils.copyProperties(entity.getOrganizacion(), org);
				dto.setOrganizacion(org);
			}

			if (entity.getUbicacion() != null) {
				UbicacionDTO ubi = new UbicacionDTO();
				BeanUtils.copyProperties(entity.getUbicacion(), ubi);
				dto.setUbicacion(ubi);
			}

		}

		return dto;
	}

	/**
	 * PersonaDTO -> PersonaEntity
	 * 
	 * @param dto PersonaDTO
	 * @return PersonaEntity
	 */
	public PersonaEntity convert(PersonaDTO dto) {

		PersonaEntity entity = null;
		if (dto != null) {
			entity = new PersonaEntity();
			BeanUtils.copyProperties(entity, dto);

			if (dto.getOrganizacion() != null) {
				OrganizacionEntity org = new OrganizacionEntity();
				BeanUtils.copyProperties(org, dto.getOrganizacion());
				entity.setOrganizacion(org);
			}

			if (dto.getUbicacion() != null) {
				UbicacionEntity ubi = new UbicacionEntity();
				BeanUtils.copyProperties(ubi, dto.getUbicacion());
				entity.setUbicacion(ubi);
			}

		}

		return entity;
	}

}
