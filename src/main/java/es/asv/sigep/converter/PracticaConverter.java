package es.asv.sigep.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import es.asv.sigep.dto.OrganizacionDTO;
import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.dto.PracticaDTO;
import es.asv.sigep.entities.OrganizacionEntity;
import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.entities.PracticaEntity;

@Component
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
				PersonaDTO alu = new PersonaDTO();
				BeanUtils.copyProperties(entity.getAlumno(), alu);
				dto.setAlumno(alu);
			}

			if (entity.getTutor() != null) {
				PersonaDTO tut = new PersonaDTO();
				BeanUtils.copyProperties(entity.getTutor(), tut);
				dto.setTutor(tut);
			}

			if (entity.getResponsable() != null) {
				PersonaDTO res = new PersonaDTO();
				BeanUtils.copyProperties(entity.getResponsable(), res);
				dto.setResponsable(res);
			}

			if (entity.getCentro() != null) {
				OrganizacionDTO cen = new OrganizacionDTO();
				BeanUtils.copyProperties(entity.getCentro(), cen);
				dto.setCentro(cen);
			}

			if (entity.getEmpresa() != null) {
				OrganizacionDTO emp = new OrganizacionDTO();
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
				PersonaEntity alu = new PersonaEntity();
				BeanUtils.copyProperties(alu, dto.getAlumno());
				entity.setAlumno(alu);
			}

			if (dto.getTutor() != null) {
				PersonaEntity tut = new PersonaEntity();
				BeanUtils.copyProperties(tut, dto.getTutor());
				entity.setTutor(tut);
			}

			if (dto.getResponsable() != null) {
				PersonaEntity res = new PersonaEntity();
				BeanUtils.copyProperties(res, dto.getResponsable());
				entity.setResponsable(res);
			}

			if (dto.getCentro() != null) {
				OrganizacionEntity cen = new OrganizacionEntity();
				BeanUtils.copyProperties(cen, dto.getCentro());
				entity.setCentro(cen);
			}

			if (dto.getEmpresa() != null) {
				OrganizacionEntity emp = new OrganizacionEntity();
				BeanUtils.copyProperties(emp, dto.getEmpresa());
				entity.setEmpresa(emp);
			}
		}

		return entity;
	}

}
