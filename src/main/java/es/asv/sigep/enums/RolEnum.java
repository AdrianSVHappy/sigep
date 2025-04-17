package es.asv.sigep.enums;

public enum RolEnum {

	P ("P", "Profesor"),
	E ("E", "Estudiante"),
	R ("R", "Responsable");
	
    private final String id;
    private final String nombre;

    private  RolEnum(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getnombre() {
        return nombre;
    }

    public static RolEnum fromId(String id) {
        for (RolEnum tipo : values()) {
            if (tipo.id.equalsIgnoreCase(id)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("ID no válido: " + id);
    }

    public static RolEnum fromnombre(String nombre) {
        for (RolEnum tipo : values()) {
            if (tipo.nombre.equalsIgnoreCase(nombre)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Descripción no válida: " + nombre);
    }
}
