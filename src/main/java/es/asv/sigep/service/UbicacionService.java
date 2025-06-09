package es.asv.sigep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.asv.sigep.converter.UbicacionConverter;
import es.asv.sigep.dto.UbicacionDTO;
import es.asv.sigep.entities.UbicacionEntity;
import es.asv.sigep.repository.UbicacionRepository;

@Service
public class UbicacionService {

	@Autowired
	private UbicacionRepository ubicacionRepository;

	@Autowired
	private UbicacionConverter ubicacionConverter;

	public UbicacionDTO save(UbicacionDTO ubicacion) {

		UbicacionEntity entity = ubicacionRepository.save(ubicacionConverter.convert(ubicacion));
		return ubicacionConverter.convert(entity);

	}

}
