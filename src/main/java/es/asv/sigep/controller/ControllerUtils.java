package es.asv.sigep.controller;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.springframework.ui.Model;

import es.asv.sigep.entities.OrganizacionEntity;
import es.asv.sigep.entities.PersonaEntity;
import es.asv.sigep.entities.PracticaEntity;
import es.asv.sigep.entities.UbicacionEntity;
import es.asv.sigep.enums.TipoEnum;

public class ControllerUtils {

	
	public static void  modelFooter(Model model) {
	
		
		String email = "adriansuarezvaldayo573@gmail.com";
		String telef = "644 63 44 62";
		String direc = "C\\ Dalí Nº 9";
		
		model.addAttribute("email", email);
		model.addAttribute("telef", telef);
		model.addAttribute("direc", direc);
		
	
	
	}

	
	
	public static List<PersonaEntity> generarAlumnosFake() {
	    List<PersonaEntity> alumnos = new ArrayList<>();

	    for (int i = 1; i <= 10; i++) {
	        PersonaEntity alumno = new PersonaEntity();
	        alumno.setId((long) i);
	        alumno.setNombre("Alumno" + i);
	        alumno.setApellidos("Apellido" + i);

	        // Organización (centro educativo)
	        OrganizacionEntity organizacion = new OrganizacionEntity();
	        organizacion.setId((long) i);
	        organizacion.setNombre("Centro Educativo " + i);
	        organizacion.setTipo(TipoEnum.I);
	        organizacion.setUbicacion(generarUbicacion("CentroCiudad" + i));
	        alumno.setOrganizacion(organizacion);

	        // Ubicación del alumno
	        alumno.setUbicacion(generarUbicacion("CiudadAlumno" + i));

	        alumnos.add(alumno);
	    }

	    return alumnos;
	}

	public static List<PracticaEntity> generarPracticasFake(List<PersonaEntity> alumnos) {
	    List<PracticaEntity> practicas = new ArrayList<>();


	    for (int i = 1; i <= 10; i++) {
	        PracticaEntity practica = new PracticaEntity();
	        practica.setIdentificador((long) i);

	        // Alumno (uno de los anteriores)
	        PersonaEntity alumno = alumnos.get(i - 1);
	        practica.setAlumno(alumno);

	        // Tutor
	        PersonaEntity tutor = new PersonaEntity();
	        tutor.setNombre("Tutor" + i);
	        tutor.setApellidos("ApellidoTutor" + i);
	        practica.setTutor(tutor);

	        // Responsable
	        PersonaEntity responsable = new PersonaEntity();
	        responsable.setNombre("Responsable" + i);
	        responsable.setApellidos("ApellidoResp" + i);
	        practica.setResponsable(responsable);

	        // Centro (educativo)
	        OrganizacionEntity centro = new OrganizacionEntity();
	        centro.setId((long) i);
	        centro.setNombre("IES Centro Formación " + i);
	        centro.setTipo(TipoEnum.I);
	        centro.setUbicacion(generarUbicacion("CiudadCentro" + i));
	        practica.setCentro(centro);

	        // Empresa
	        OrganizacionEntity empresa = new OrganizacionEntity();
	        empresa.setId((long) (i + 100));
	        empresa.setNombre("Empresa S.A. " + i);
	        empresa.setTipo(TipoEnum.E);
	        empresa.setUbicacion(generarUbicacion("CiudadEmpresa" + i));
	        practica.setEmpresa(empresa);

	        // Fechas y duración
	        practica.setDuracion(120 + i * 5);
	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DAY_OF_MONTH, -i * 30);
	        practica.setInicio(cal.getTime());

	        cal.add(Calendar.DAY_OF_MONTH, 60);
	        practica.setFin(cal.getTime());

	        practica.setNumeroSeguridadSocial("SSN000" + i);

	        practicas.add(practica);
	    }

	    return practicas;
	}

	private static UbicacionEntity generarUbicacion(String ciudadBase) {
	    UbicacionEntity ubicacion = new UbicacionEntity();
	    ubicacion.setPais("España");
	    ubicacion.setComunidad("Comunidad de " + ciudadBase);
	    ubicacion.setProvincia("Provincia de " + ciudadBase);
	    ubicacion.setCiudad(ciudadBase);
	    ubicacion.setCodigoPostal("460" + new Random().nextInt(10)); // Ejemplo: 4601, 4602...
	    ubicacion.setDireccion("Calle " + ciudadBase + ", Nº" + (10 + new Random().nextInt(90)));
	    return ubicacion;
	}

	
}
