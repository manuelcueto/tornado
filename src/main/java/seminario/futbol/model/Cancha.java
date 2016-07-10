package seminario.futbol.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "canchas")
public class Cancha {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idCancha;
    private String direccion;
    private String nombre;
    private String telefono;
    private String dueno;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    public Integer getIdCancha() {
	return idCancha;
    }

    public String getDireccion() {
	return direccion;
    }

    public String getNombre() {
	return nombre;
    }

    public String getTelefono() {
	return telefono;
    }

    public String getDueno() {
	return dueno;
    }

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

    public Estado getEstado() {
	return estado;
    }

    public void setEstado(Estado estado) {
	this.estado = estado;
    }

    public boolean sosLaCancha(String nombreCancha) {
	return this.nombre.equals(nombreCancha);
    }

}
