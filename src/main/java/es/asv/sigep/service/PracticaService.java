package es.asv.sigep.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private PracticaRepository practicaRepository;

	@Autowired
	private PracticaConverter practicaConverter;

	@Autowired
	private PersonaRepository personaRepository;

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

}
