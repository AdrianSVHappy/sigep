package es.asv.sigep.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.asv.sigep.SigepApplication;
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

    private final SigepApplication sigepApplication;

	@Autowired
	private PracticaRepository practicaRepository;

	@Autowired
	private PracticaConverter practicaConverter;

	@Autowired
	private PersonaRepository personaRepository;

    PracticaService(SigepApplication sigepApplication) {
        this.sigepApplication = sigepApplication;
    }

	public List<PracticaDTO> findAllByTutor(Long tutorId) {

		PersonaEntity tutor = personaRepository.findById(tutorId).orElse(null);
		List<PracticaDTO> listaDto = new ArrayList<>();

		if (tutor != null) {
			List<PracticaEntity> listaEntity = practicaRepository.findAllByTutor(tutor);

			for (PracticaEntity entity : listaEntity) {
				listaDto.add(practicaConverter.convert(entity));
			}
		}
		return listaDto;

	}

	public boolean existsByTutorAndAlumno(Long idTutor, Long idAlumno) {
		
		
		PersonaEntity tutor = null;
		
		//SI existe el tutor
		if((idTutor != null) && (personaRepository.findById(idTutor) != null)) {
			tutor = personaRepository.findById(idTutor).orElse(null);
		}else {
			return false;
		}
		
		
		PersonaEntity alumno = null;
		
		//SI eexiste el alumno
		if((idAlumno != null) && (personaRepository.findById(idAlumno) != null)) {
		 alumno = personaRepository.findById(idAlumno).orElse(null);
		}else {
			return false;
		}
		
		//SI el tutor gestiona la practica del alumno
		return practicaRepository.existsByTutorAndAlumno(tutor, alumno);
	}

	public PracticaDTO findById(Long id) {
		PracticaEntity practica = null;
		
		//Si el id no es nulo
		if(id != null ) {
			
			practica = practicaRepository.findById(id).orElse(null);
			
		}
		
		return practicaConverter.convert(practica);
	}

	public PracticaDTO findByAlumno(Long alumnoId) {
		PracticaEntity practica = null;
		PersonaEntity alumno = personaRepository.findById(alumnoId).orElse(null);
		
		//Si el alumno no es nulo
		if(alumno != null) {
			practica = practicaRepository.findByAlumno(alumno).orElse(null);
		}
		
		return practicaConverter.convert(practica);
	}
	
}
