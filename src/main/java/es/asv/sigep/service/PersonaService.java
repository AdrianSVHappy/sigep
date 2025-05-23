package es.asv.sigep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.asv.sigep.converter.PersonaConverter;
import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.repository.PersonaRepository;

@Service
public class PersonaService {

	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private PersonaConverter personaConverter;
	
	
	public PersonaDTO findById(Long id) {
		
		//Busca a la persona
		PersonaEntity personaEntity = personaRepository.findById(id).orElse(null);
		
		//Convertimos de Entity a dto
		return personaConverter.convert(personaEntity);
		
	}
	
}
