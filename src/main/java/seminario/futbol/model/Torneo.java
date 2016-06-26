package seminario.futbol.model;

import java.util.Date;
import java.util.List;

public class Torneo {

    private String nombre;
    private EstadoTorneo estado;
    private Integer categoria;
    private Date inicio;
    private List<Cancha> canchas;
    private List<Arbitro> arbitro;
    private List<Equipo> equipos;
    private List<Posicion> posiciones;
    private List<Suspension> suspensiones;
}
