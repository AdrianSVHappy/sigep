package es.asv.sigep.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.asv.sigep.SigepApplication;
import es.asv.sigep.controller.PersonaController;
import es.asv.sigep.converter.PracticaConverter;
import es.asv.sigep.converter.RegistroConverter;
import es.asv.sigep.dto.PracticaDTO;
import es.asv.sigep.dto.RegistroDTO;
import es.asv.sigep.entities.PracticaEntity;
import es.asv.sigep.entities.RegistroEntity;
import es.asv.sigep.repository.PracticaRepository;
import es.asv.sigep.repository.RegistroRepository;

@Service
public class RegistroService {

	private final SigepApplication sigepApplication;

	@Autowired
	private RegistroRepository registroRepository;

	@Autowired
	private PracticaRepository practicaRepository;
	
	@Autowired
	private RegistroConverter registroConverter;

	@Autowired
	private PracticaConverter practicaConverter;

	RegistroService(SigepApplication sigepApplication) {
		this.sigepApplication = sigepApplication;
	}

	public boolean existsByPracticaAndFecha(PracticaDTO practica, LocalDate fecha) {

		if (practica != null && fecha != null) {
			PracticaEntity entity = practicaConverter.convert(practica);
			return registroRepository.existsByPracticaAndFecha(entity, fecha);
		}

		return false;

	}

	public List<RegistroDTO> findAllByPracticaOrderByFechaAsc(Long idPractica) { 
		
		PracticaEntity practicaEntity = practicaRepository.findById(idPractica).orElse(null);
		List<RegistroDTO> listaDto = new ArrayList<>();
		
		if(practicaEntity != null) {
			List<RegistroEntity> listaEntity = registroRepository.findAllByPracticaOrderByFechaAsc(practicaEntity);
		
			for (RegistroEntity entity : listaEntity) {
				listaDto.add(registroConverter.convert(entity));
			}
		
		}
		
		return listaDto;
	}
	
	
	public RegistroDTO findByPracticaAndFecha(Long idPractica, LocalDate fecha){
		
		
		PracticaEntity practica = practicaRepository.findById(idPractica).orElse(null);
		RegistroDTO dto = null;
		
		
		if(practica != null)
			dto = registroConverter.convert( registroRepository.findByPracticaAndFecha(practica, fecha).orElse(null));
		
		return dto;
		
	}

	public RegistroDTO save(RegistroDTO registro) {
		
		RegistroDTO ret = null;
		
		if(registro != null) {
			
			RegistroEntity entity = registroConverter.convert(registro);
			
			if(entity.getPractica() != null) {
				entity = registroRepository.save(registroConverter.convert(registro));
				
				ret = registroConverter.convert(entity);
			}
		}
		return ret;
	}

	public boolean existsById(Long id) {
		return registroRepository.existsById(id);
	}
}
