package es.asv.sigep.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.asv.sigep.SigepApplication;
import es.asv.sigep.converter.MensajeConverter;
import es.asv.sigep.converter.PersonaConverter;
import es.asv.sigep.dto.MensajeDTO;
import es.asv.sigep.dto.PersonaDTO;
import es.asv.sigep.entities.MensajeEntity;
import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.repository.MensajeRepository;
import es.asv.sigep.repository.PersonaRepository;
import es.asv.sigep.repository.PracticaRepository;

@Service
public class MensajeService {

	private final SigepApplication sigepApplication;
	
	MensajeService(SigepApplication sigepApplication) {
		this.sigepApplication = sigepApplication;
	}
	
	@Autowired
	private MensajeRepository mensajeRepository;
	
	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private MensajeConverter mensajeConverter;
	
	@Autowired
	private PersonaConverter personaConverter;
	
	
	
	public List<MensajeDTO> findAllByAutorOrReceptorOrderByFechaDesc(PersonaDTO persona){
		
		PersonaEntity personaEntity = personaConverter.convert(persona);
		List<MensajeDTO> listadoDto = new ArrayList<MensajeDTO>();
	
		if(personaEntity != null) {
			List<MensajeEntity> listadoEntity = mensajeRepository.findAllByAutorOrReceptorOrderByFechaDesc(personaEntity, personaEntity);
		
			for(MensajeEntity entity : listadoEntity) {
				listadoDto.add(mensajeConverter.convert(entity));
			}
		
		}
		
		return listadoDto;
	}



	public MensajeDTO save(MensajeDTO mensaje) {
		
		MensajeDTO ret = null;
		
		if (mensaje != null) {
			MensajeEntity entity = mensajeConverter.convert(mensaje);
			
			entity = mensajeRepository.save(entity);
			ret = mensajeConverter.convert(entity);
		}
		
		return ret;
	}
	
}
