package es.asv.sigep.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.asv.sigep.dto.PracticaDTO;
import es.asv.sigep.dto.RegistroDTO;
import es.asv.sigep.entities.PracticaEntity;
import es.asv.sigep.entities.RegistroEntity;

@Component
public class RegistroConverter {

	@Autowired
	private PracticaConverter practicaConverter;

	/**
	 * RegistroEntity -> RegistroDTO
	 * 
	 * @param entity RegistroEntity
	 * @return RegistroDTO
	 */
	public RegistroDTO convert(RegistroEntity entity) {

		RegistroDTO dto = null;

		if (entity != null) {
			dto = new RegistroDTO();
			BeanUtils.copyProperties(entity, dto);

			if (entity.getPractica() != null) {
				PracticaDTO pra = practicaConverter.convert(entity.getPractica());
				dto.setPractica(pra);
			}
		}

		return dto;
	}

	/**
	 * RegistroDTO -> RegistroEntity
	 * 
	 * @param dto RegistroDTO
	 * @return RegistroEntity
	 */
	public RegistroEntity convert(RegistroDTO dto) {

		RegistroEntity entity = null;

		if (dto != null) {
			entity = new RegistroEntity();
			BeanUtils.copyProperties(dto, entity);

			if (dto.getPractica() != null) {
				PracticaEntity pra = practicaConverter.convert(dto.getPractica());
				entity.setPractica(pra);
			}
		}

		return entity;
	}

}
