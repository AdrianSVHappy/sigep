package es.asv.sigep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.asv.sigep.converter.OrganizacionConverter;
import es.asv.sigep.repository.OrganizacionRepository;

@Service
public class OrganizacionService {

	
	@Autowired
	private OrganizacionRepository organizacionRepository;
	
	@Autowired
	private OrganizacionConverter organizacionConverter;
	
}
