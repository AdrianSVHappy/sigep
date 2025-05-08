package es.asv.sigep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.asv.sigep.converter.UbicacionConverter;
import es.asv.sigep.repository.UbicacionRepository;

@Service
public class UbicacionService {

	@Autowired
	private UbicacionRepository ubicacionRepository;
	
	@Autowired
	private UbicacionConverter ubicacionConverter;
	
}
