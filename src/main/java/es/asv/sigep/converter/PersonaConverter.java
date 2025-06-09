package es.asv.sigep.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.asv.sigep.dto.OrganizacionDTO;
import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.dto.UbicacionDTO;
import es.asv.sigep.entities.OrganizacionEntity;
import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.entities.UbicacionEntity;

@Component
public class PersonaConverter {

	@Autowired
	private OrganizacionConverter organizacionConverter;

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
				OrganizacionDTO org = organizacionConverter.convert(entity.getOrganizacion());
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
			BeanUtils.copyProperties(dto, entity);

			if (dto.getOrganizacion() != null) {
				OrganizacionEntity org = organizacionConverter.convert(dto.getOrganizacion());

				entity.setOrganizacion(org);
			}

			if (dto.getUbicacion() != null) {
				UbicacionEntity ubi = new UbicacionEntity();
				BeanUtils.copyProperties(dto.getUbicacion(), ubi);
				entity.setUbicacion(ubi);
			}

		}

		return entity;
	}

}
