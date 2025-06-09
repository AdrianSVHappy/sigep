package es.asv.sigep.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.asv.sigep.dto.MensajeDTO;
import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.dto.RegistroDTO;
import es.asv.sigep.entities.MensajeEntity;
import es.asv.sigep.entities.PersonaEntity;


@Component
public class MensajeConverter {

	@Autowired
	private PersonaConverter personaConverter;

	/**
	 * MensajeEntity -> MensajeDTO
	 * 
	 * @param entity MensajeEntity
	 * @return MensajeDTO
	 */
	public MensajeDTO convert(MensajeEntity entity) {

		MensajeDTO dto = null;

		if (entity != null) {
			dto = new MensajeDTO();
			BeanUtils.copyProperties(entity, dto);

			if (entity.getAutor() != null) {
				PersonaDTO autor = personaConverter.convert(entity.getAutor());
				dto.setAutor(autor);
			}

			if (entity.getReceptor() != null) {
				PersonaDTO receptor = personaConverter.convert(entity.getReceptor());
				dto.setReceptor(receptor);
			}
		}

		return dto;

	}

	/**
	 * MensajeDTO -> MensajeEntity
	 * 
	 * @param dto MensajeDTO
	 * @return MensajeEntity
	 */
	public MensajeEntity convert(MensajeDTO dto) {

		MensajeEntity entity = null;

		if (dto != null) {
			entity = new MensajeEntity();
			BeanUtils.copyProperties(dto, entity);

			if (dto.getAutor() != null) {
				PersonaEntity autor = personaConverter.convert(dto.getAutor());
				entity.setAutor(autor);
			}

			if (dto.getReceptor() != null) {
				PersonaEntity receptor = personaConverter.convert(dto.getReceptor());
				entity.setReceptor(receptor);
			}
		}

		return entity;
	}

}
