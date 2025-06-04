package es.asv.sigep.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import es.asv.sigep.dto.MensajeDTO;
import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.dto.RegistroDTO;
import es.asv.sigep.entities.MensajeEntity;
import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.entities.PracticaEntity;
import es.asv.sigep.entities.RegistroEntity;

@Component
public class MensajeConverter {

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
				PersonaDTO autor = new PersonaDTO();
				BeanUtils.copyProperties(entity.getAutor(), autor);
				dto.setAutor(autor);
			}

			if (entity.getReceptor() != null) {
				PersonaDTO receptor = new PersonaDTO();
				BeanUtils.copyProperties(entity.getReceptor(), receptor);
				dto.setReceptor(receptor);
			}
		}

		return dto;

	}

	/**
	 * MensajeDTO -> MensajeEntity
	 * @param dto MensajeDTO
	 * @return MensajeEntity
	 */
	public MensajeEntity convert(MensajeDTO dto) {

		MensajeEntity entity = null;

		if (dto != null) {
			entity = new MensajeEntity();
			BeanUtils.copyProperties(entity, dto);

			if (dto.getAutor() != null) {
				PersonaEntity autor = new PersonaEntity();
				BeanUtils.copyProperties(autor, dto.getAutor());
				entity.setAutor(autor);
			}

			if (dto.getReceptor() != null) {
				PersonaEntity receptor = new PersonaEntity();
				BeanUtils.copyProperties(receptor, dto.getReceptor());
				entity.setReceptor(receptor);
			}
		}

		return entity;
	}

}
