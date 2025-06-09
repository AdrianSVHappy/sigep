package es.asv.sigep.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.asv.sigep.SigepApplication;
import es.asv.sigep.converter.PersonaConverter;
import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.enums.RolEnum;
import es.asv.sigep.repository.PersonaRepository;

@Service
public class PersonaService {

	@Autowired
	private OrganizacionService organizacionService;
	
	@Autowired
	private UbicacionService ubicacionService; 

	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private PersonaConverter personaConverter;



	public PersonaDTO findById(Long id) {

		// Busca a la persona
		PersonaEntity personaEntity = personaRepository.findById(id).orElse(null);

		// Convertimos de Entity a dto
		return personaConverter.convert(personaEntity);

	}

	public PersonaDTO save(PersonaDTO persona) {

	    if (persona.getOrganizacion() != null && persona.getOrganizacion().getId() == null) {
	        persona.setOrganizacion(organizacionService.save(persona.getOrganizacion()));
	    }

	    if (persona.getUbicacion() != null && persona.getUbicacion().getId() == null) {
	        persona.setUbicacion(ubicacionService.save(persona.getUbicacion()));
	    }
		
		PersonaEntity guardado = personaRepository.save(personaConverter.convert(persona));

		return personaConverter.convert(guardado);
	}

	public List<PersonaDTO> findAllByRol(RolEnum r) {

		List<PersonaEntity> entityList = personaRepository.findAllByRol(r);
		List<PersonaDTO> dtoList = new ArrayList<>();

		for (PersonaEntity entity : entityList) {

			dtoList.add(personaConverter.convert(entity));

		}

		return dtoList;
	}

}
