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

    public Arbitro() {

    }

    public Arbitro(String email, String nombre, String telefono) {
	super();
	this.email = email;
	this.nombre = nombre;
	this.telefono = telefono;
    }

}
