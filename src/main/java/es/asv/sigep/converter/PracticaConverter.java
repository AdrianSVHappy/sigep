package es.asv.sigep.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.asv.sigep.dto.OrganizacionDTO;
import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.dto.PracticaDTO;
import es.asv.sigep.entities.OrganizacionEntity;
import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.entities.PracticaEntity;

@Component
public class PracticaConverter {

	@Autowired
	private PersonaConverter personaConverter;

	@Autowired
	private OrganizacionConverter organizacionConverter;

	/**
	 * PracticaEntity -> PracticaDTO
	 * 
	 * @param entity PracticaEntity
	 * @return PracticaDTO
	 */
	public PracticaDTO convert(PracticaEntity entity) {

		PracticaDTO dto = null;
		if (entity != null) {
			dto = new PracticaDTO();
			BeanUtils.copyProperties(entity, dto);

			if (entity.getAlumno() != null) {
				PersonaDTO alu = personaConverter.convert(entity.getAlumno());
				dto.setAlumno(alu);
			}

			if (entity.getTutor() != null) {
				PersonaDTO tut = personaConverter.convert(entity.getTutor());
				dto.setTutor(tut);
			}

			if (entity.getResponsable() != null) {
				PersonaDTO res = personaConverter.convert(entity.getResponsable());
				dto.setResponsable(res);
			}

			if (entity.getCentro() != null) {
				OrganizacionDTO cen = organizacionConverter.convert(entity.getCentro());
				dto.setCentro(cen);
			}

			if (entity.getEmpresa() != null) {
				OrganizacionDTO emp = organizacionConverter.convert(entity.getEmpresa());
				dto.setEmpresa(emp);
			}

		}

		return dto;
	}

	/**
	 * PracticaDTO -> PracticaEntity
	 * 
	 * @param dto PracticaDTO
	 * @return PracticaEntity
	 */
	public PracticaEntity convert(PracticaDTO dto) {

		PracticaEntity entity = null;
		if (dto != null) {
			entity = new PracticaEntity();
			BeanUtils.copyProperties(dto, entity);

			if (dto.getAlumno() != null) {
				PersonaEntity alu = personaConverter.convert(dto.getAlumno());
				entity.setAlumno(alu);
			}

			if (dto.getTutor() != null) {
				PersonaEntity tut = personaConverter.convert(dto.getTutor());
				entity.setTutor(tut);
			}

			if (dto.getResponsable() != null) {
				PersonaEntity res = personaConverter.convert(dto.getResponsable());
				entity.setResponsable(res);
			}

			if (dto.getCentro() != null) {
				OrganizacionEntity cen = organizacionConverter.convert(dto.getCentro());
				entity.setCentro(cen);
			}

			if (dto.getEmpresa() != null) {
				OrganizacionEntity emp = organizacionConverter.convert(dto.getEmpresa());
				entity.setEmpresa(emp);
			}

		}

		return entity;
	}

}
