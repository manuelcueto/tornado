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
    private String telefono;
    private String dueno;

    public void setDireccion(String direccion) {
	this.direccion = direccion;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
	this.telefono = telefono;
    }

    public void setDueno(String dueno) {
	this.dueno = dueno;
    }

    public boolean sosLaCancha(String nombreCancha) {
	return this.nombre.equals(nombreCancha);
    }

}
