package es.asv.sigep.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.asv.sigep.converter.OrganizacionConverter;
import es.asv.sigep.dto.OrganizacionDTO;
import es.asv.sigep.entities.OrganizacionEntity;
import es.asv.sigep.enums.TipoEnum;
import es.asv.sigep.repository.OrganizacionRepository;

@Service
public class OrganizacionService {

	@Autowired
	private UbicacionService ubicacionService;
	
	@Autowired
	private OrganizacionRepository organizacionRepository;
	
	@Autowired
	private OrganizacionConverter organizacionConverter;

	public List<OrganizacionDTO> findAll() {
		
		List<OrganizacionDTO> listaDTO = new ArrayList<>();
		List<OrganizacionEntity> listaEntity = organizacionRepository.findAll();
		
		for(OrganizacionEntity entity : listaEntity) {
			listaDTO.add(organizacionConverter.convert(entity));
		}
		
		return listaDTO;
	}

	public List<OrganizacionDTO> findAllByTipo(TipoEnum t) {
		List<OrganizacionDTO> listaDTO = new ArrayList<>();
		List<OrganizacionEntity> listaEntity = organizacionRepository.findAllByTipo(t);
		
		for(OrganizacionEntity entity : listaEntity) {
			listaDTO.add(organizacionConverter.convert(entity));
		}
		
		return listaDTO;
	}

	public OrganizacionDTO findById(Long id) {
		
		OrganizacionEntity entity = organizacionRepository.findById(id).orElse(null);
		
		return organizacionConverter.convert(entity);
	}

	public OrganizacionDTO save(OrganizacionDTO org) {
		
	    if (org.getUbicacion() != null && org.getUbicacion().getId() == null) {
	        org.setUbicacion(ubicacionService.save(org.getUbicacion()));
	    }
		
		OrganizacionEntity entity = organizacionConverter.convert(org);
		
		entity = organizacionRepository.save(entity);
		
		return organizacionConverter.convert(entity);
	}
	
}
