package es.asv.sigep.converter;

import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

import es.asv.sigep.dto.UbicacionDTO;
import es.asv.sigep.entities.UbicacionEntity;

@Component
public class UbicacionConverter {

	/**
	 * UbicacionEntity -> UbicacionDTO
	 * 
	 * @param entity UbicacionEntity
	 * @return UbicacionDTO
	 */
	public UbicacionDTO convert(UbicacionEntity entity) {

		UbicacionDTO dto = null;
		if (entity != null) {
			dto = new UbicacionDTO();
			BeanUtils.copyProperties(entity, dto);
		}

		return dto;
	}

	/**
	 * UbicacionDTO -> UbicacionEntity
	 * 
	 * @param dto UbicacionDTO
	 * @return UbicacionEntity
	 */
	public UbicacionEntity convert(UbicacionDTO dto) {

		UbicacionEntity entity = null;
		if (dto != null) {
			entity = new UbicacionEntity();
			BeanUtils.copyProperties(entity, dto);
		}

		return entity;
	}
}
