package es.asv.sigep.converter;

import org.springframework.beans.BeanUtils;

import es.asv.sigep.dto.OrganizacionDTO;
import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.dto.PracticaDTO;
import es.asv.sigep.entities.OrganizacionEntity;
import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.entities.PracticaEntity;

public class PracticaConverter {

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
				PersonaDTO alu = null;
				BeanUtils.copyProperties(entity.getAlumno(), alu);
				dto.setAlumno(alu);
			}

			if (entity.getTutor() != null) {
				PersonaDTO tut = null;
				BeanUtils.copyProperties(entity.getTutor(), tut);
				dto.setTutor(tut);
			}

			if (entity.getResponsable() != null) {
				PersonaDTO res = null;
				BeanUtils.copyProperties(entity.getResponsable(), res);
				dto.setResponsable(res);
			}

			if (entity.getCentro() != null) {
				OrganizacionDTO cen = null;
				BeanUtils.copyProperties(entity.getCentro(), cen);
				dto.setCentro(cen);
			}

			if (entity.getEmpresa() != null) {
				OrganizacionDTO emp = null;
				BeanUtils.copyProperties(entity.getEmpresa(), emp);
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
			BeanUtils.copyProperties(entity, dto);

			if (dto.getAlumno() != null) {
				PersonaEntity alu = null;
				BeanUtils.copyProperties(alu, dto.getAlumno());
				entity.setAlumno(alu);
			}

			if (dto.getTutor() != null) {
				PersonaEntity tut = null;
				BeanUtils.copyProperties(tut, dto.getTutor());
				entity.setTutor(tut);
			}

			if (dto.getResponsable() != null) {
				PersonaEntity res = null;
				BeanUtils.copyProperties(res, dto.getResponsable());
				entity.setResponsable(res);
			}

			if (dto.getCentro() != null) {
				OrganizacionEntity cen = null;
				BeanUtils.copyProperties(cen, dto.getCentro());
				entity.setCentro(cen);
			}

			if (dto.getEmpresa() != null) {
				OrganizacionEntity emp = null;
				BeanUtils.copyProperties(emp, dto.getEmpresa());
				entity.setEmpresa(emp);
			}
		}

		return entity;
	}

}
