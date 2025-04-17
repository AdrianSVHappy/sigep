package es.asv.sigep.enums;

public enum TipoEnum {
	
    I ("I", "Instituto"),
    U ("U", "Universidad"),
    E ("E", "Empresa");


    private final String id;
    private final String nombre;

    private  TipoEnum(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getnombre() {
        return nombre;
    }

    public static TipoEnum fromId(String id) {
        for (TipoEnum tipo : values()) {
            if (tipo.id.equalsIgnoreCase(id)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("ID no válido: " + id);
    }

    public static TipoEnum fromnombre(String nombre) {
        for (TipoEnum tipo : values()) {
            if (tipo.nombre.equalsIgnoreCase(nombre)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Descripción no válida: " + nombre);
    }
}
