package seminario.futbol.model;

public class Cancha {

    private String direccion;
    private String nombre;

    public Cancha(String direccion, String nombre) {
	super();
	this.direccion = direccion;
	this.nombre = nombre;
    }

    public boolean sosLaCancha(String nombreCancha) {
	return this.nombre.equals(nombreCancha);
    }

}
