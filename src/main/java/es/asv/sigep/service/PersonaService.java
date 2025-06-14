package es.asv.sigep.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.asv.sigep.converter.PersonaConverter;
import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.entities.PermisoEntity;
import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.entities.UsuarioEntity;
import es.asv.sigep.enums.RolEnum;
import es.asv.sigep.repository.PermisoRepository;
import es.asv.sigep.repository.PersonaRepository;
import es.asv.sigep.repository.UsuarioRepository;

@Service
public class PersonaService {

	@Autowired
	private OrganizacionService organizacionService;

	@Autowired
	private UbicacionService ubicacionService;

	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PermisoRepository permisoRepository;

	@Autowired
	private PersonaConverter personaConverter;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public PersonaDTO findById(Long id) {

		if (id == null) {
			return null;
		}

		// Busca a la persona
		PersonaEntity personaEntity = personaRepository.findById(id).orElse(null);

		// Convertimos de Entity a dto
		return personaConverter.convert(personaEntity);

	}

	public PersonaDTO save(PersonaDTO persona) {

		if (persona.getOrganizacion() != null) {
			persona.setOrganizacion(organizacionService.save(persona.getOrganizacion()));
		}

		if (persona.getUbicacion() != null) {
			persona.setUbicacion(ubicacionService.save(persona.getUbicacion()));
		}

		if (persona.getId() == null && persona.getRol() != null && persona.getRol() != RolEnum.R) {
			UsuarioEntity usr = new UsuarioEntity(persona.getEmail(), passwordEncoder.encode("usuario"), true);
			usr = usuarioRepository.save(usr);
			PermisoEntity per = new PermisoEntity(persona.getEmail(), "ROLE_" + persona.getRol().getId(), usr);
			per = permisoRepository.save(per);
		}

		if(persona.getApellidos() == null) {
			persona.setApellidos("");
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

	public PersonaDTO findByEmail(String email) {

		if (email == null)
			return null;

		PersonaEntity entity = personaRepository.findByEmail(email);

		return personaConverter.convert(entity);
	}



	public void actualizarPass(String email, String pass) {
		
		Optional<UsuarioEntity> usr = usuarioRepository.findById(email);
		
		if(usr != null) {
			usr.get().setPassw(passwordEncoder.encode(pass));
			usuarioRepository.save(usr.get());
		}
	}
}
