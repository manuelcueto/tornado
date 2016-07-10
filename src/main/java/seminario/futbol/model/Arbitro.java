package seminario.futbol.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "arbitros")
public class Arbitro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idArbitro;
    private String email;
    private String nombre;
    private String telefono;

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public String getTelefono() {
	return telefono;
    }

    public void setTelefono(String telefono) {
	this.telefono = telefono;
    }

    public Integer getIdArbitro() {
	return idArbitro;
    }

}
