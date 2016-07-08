package seminario.futbol.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "canchas")
public class Cancha {

    @Id
    private Integer idCancha;
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
