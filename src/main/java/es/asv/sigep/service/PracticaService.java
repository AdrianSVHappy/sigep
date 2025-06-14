package es.asv.sigep.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.asv.sigep.SigepApplication;
import es.asv.sigep.controller.PracticasController;
import es.asv.sigep.converter.PersonaConverter;
import es.asv.sigep.converter.PracticaConverter;
import es.asv.sigep.converter.UbicacionConverter;
import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.dto.PracticaDTO;
import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.entities.PracticaEntity;
import es.asv.sigep.repository.PersonaRepository;
import es.asv.sigep.repository.PracticaRepository;

@Service
public class PracticaService {

	@Autowired
	private PersonaService personaService;

	@Autowired
	private OrganizacionService organizacionService;

	@Autowired
	private PracticaRepository practicaRepository;

	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private PracticaConverter practicaConverter;

	@Autowired
	private PersonaConverter personaConverter;

	public List<PracticaDTO> findAllByTutor(PersonaDTO tutorDto) {

		PersonaEntity tutor = personaConverter.convert(tutorDto);
		List<PracticaDTO> listaDto = new ArrayList<>();

		if (tutor != null) {
			List<PracticaEntity> listaEntity = practicaRepository.findAllByTutor(tutor);

			for (PracticaEntity entity : listaEntity) {
				listaDto.add(practicaConverter.convert(entity));
			}
		}
		return listaDto;

	}

	public boolean existsByTutorAndAlumno(PersonaDTO tutorDto, PersonaDTO alumnoDto) {

		PersonaEntity tutor = null;

		// SI existe el tutor
		if (tutorDto != null) {
			tutor = personaConverter.convert(tutorDto);
		} else {
			return false;
		}

		PersonaEntity alumno = null;

		// SI eexiste el alumno
		if (alumnoDto != null) {
			alumno = personaConverter.convert(alumnoDto);
		} else {
			return false;
		}

		// SI el tutor gestiona la practica del alumno
		return practicaRepository.existsByTutorAndAlumno(tutor, alumno);
	}

	public PracticaDTO findById(Long id) {
		PracticaEntity practica = null;

		// Si el id no es nulo
		if (id != null) {

			practica = practicaRepository.findById(id).orElse(null);

		}

		return practicaConverter.convert(practica);
	}

	public PracticaDTO findByAlumno(PersonaDTO alumno) {
		PracticaEntity practica = null;
		PersonaEntity alumnoE = personaConverter.convert(alumno);

		// Si el alumno no es nulo
		if (alumnoE != null) {
			practica = practicaRepository.findByAlumno(alumnoE).orElse(null);
		}

		return practicaConverter.convert(practica);
	}

	public PracticaDTO save(PracticaDTO practica) {

		// Guardado o actualización en cascada
		if (practica.getTutor() != null && practica.getTutor().getId() == null) {
			practica.setTutor(personaService.save(practica.getTutor()));
		}

		if (practica.getAlumno() != null && practica.getAlumno().getId() == null) {

			if (practica.getAlumno().getOrganizacion().getId() == null) {
				practica.getAlumno().setOrganizacion(practica.getTutor().getOrganizacion());
			}

			practica.setAlumno(personaService.save(practica.getAlumno()));
		}

		if (practica.getResponsable() != null) {
			practica.setResponsable(personaService.save(practica.getResponsable()));
		}

		if (practica.getCentro() != null) {

			if (practica.getCentro().getId() == null) {
				practica.setCentro(practica.getTutor().getOrganizacion());
			}

			practica.setCentro(organizacionService.save(practica.getCentro()));
		}

		if (practica.getEmpresa() != null) {

			if (practica.getEmpresa().getId() == null) {
				practica.setEmpresa(practica.getResponsable().getOrganizacion());
			}

			practica.setEmpresa(organizacionService.save(practica.getEmpresa()));
		}

		PracticaEntity entity = practicaConverter.convert(practica);

		entity = practicaRepository.save(entity);

		return practicaConverter.convert(entity);

	}

	public boolean existsBynumeroSeguridadSocial(String nss) {

		return practicaRepository.existsBynumeroSeguridadSocial(nss);
	}

	public boolean existsByNumeroSeguridadSocialAndIdNot(String nss, Long id) {

		return practicaRepository.existsByNumeroSeguridadSocialAndIdNot(nss, id);

	}

	public boolean existsById(Long id) {
		return practicaRepository.existsById(id);
	}

}
