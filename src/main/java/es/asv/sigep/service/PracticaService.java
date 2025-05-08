package es.asv.sigep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.asv.sigep.converter.PracticaConverter;
import es.asv.sigep.repository.PracticaRepository;

@Service
public class PracticaService {

	@Autowired
	private PracticaRepository practicaRepository;
	
	@Autowired
	private PracticaConverter practicaConverter;
	
	
}
