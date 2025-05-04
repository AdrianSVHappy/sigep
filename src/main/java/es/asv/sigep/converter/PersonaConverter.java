package es.asv.sigep.converter;

import org.springframework.beans.BeanUtils;

import es.asv.sigep.dto.OrganizacionDTO;
import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.dto.UbicacionDTO;
import es.asv.sigep.entities.OrganizacionEntity;
import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.entities.UbicacionEntity;

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
				OrganizacionDTO org = null;
				BeanUtils.copyProperties(entity.getOrganizacion(), org);
				dto.setOrganizacion(org);
			}
			
			if (entity.getUbicacion() != null) {
				UbicacionDTO ubi = null;
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
				OrganizacionEntity org = null;
				BeanUtils.copyProperties(org, dto.getOrganizacion());
				entity.setOrganizacion(org);
			}
			
			if (dto.getUbicacion() != null) {
				UbicacionEntity ubi = null;
				BeanUtils.copyProperties(ubi, dto.getUbicacion());
				entity.setUbicacion(ubi);
			}
			
		}

		return entity;
	}
	
}
