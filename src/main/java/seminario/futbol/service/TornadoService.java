package seminario.futbol.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import seminario.futbol.estadisticas.EstadisticasEquipo;
import seminario.futbol.estadisticas.EstadisticasJugador;
import seminario.futbol.estadisticas.EstadisticasPartido;
import seminario.futbol.estadisticas.TablaGeneral;
import seminario.futbol.model.Cancha;
import seminario.futbol.model.Equipo;
import seminario.futbol.model.Jugador;
import seminario.futbol.model.Partido;
import seminario.futbol.model.Resultado;
import seminario.futbol.model.TipoTarjeta;
import seminario.futbol.model.Torneo;
import seminario.futbol.model.factories.CanchaFactory;
import seminario.futbol.model.factories.EquipoFactory;
import seminario.futbol.model.factories.GolFactory;
import seminario.futbol.model.factories.JugadorFactory;
import seminario.futbol.model.factories.TarjetaFactory;
import seminario.futbol.repositories.CanchaRepository;
import seminario.futbol.repositories.EquipoRepository;
import seminario.futbol.repositories.GolRepository;
import seminario.futbol.repositories.JugadorRepository;
import seminario.futbol.repositories.PartidoRepository;
import seminario.futbol.repositories.TarjetaRepository;
import seminario.futbol.repositories.TorneoRepository;

@Service
@Transactional
public class TornadoService {

    @Autowired
    private TorneoRepository torneoRepo;
    @Autowired
    private EquipoRepository equipoRepo;
    @Autowired
    private JugadorRepository jugadorRepo;
    @Autowired
    private CanchaRepository canchaRepo;
    @Autowired
    private PartidoRepository partidoRepo;
    @Autowired
    private GolRepository golRepo;
    @Autowired
    private TarjetaRepository tarjetaRepo;

    // canchas: crear, modificar, borrar, listar

    public void crearCancha(String nombre, String direccion, String dueno, String telefono) {
	CanchaFactory fact = new CanchaFactory();
	this.canchaRepo
		.save(fact.withNombre(nombre).withDireccion(direccion).withDueno(dueno).withTelefono(telefono).build());
    }

    public void modificarCancha(Integer idCancha, String nombre, String direccion, String dueno, String telefono) {
	this.canchaRepo.modificarCancha(dueno, telefono, nombre, direccion, idCancha);
    }

    public void borrarCancha(Integer idCancha) {
	this.canchaRepo.delete(idCancha);
    }

    public Iterable<Cancha> listarCanchas() {
	return this.canchaRepo.findAll();
    }

    // Equipos: Crear, borrar, elegir capitan, asociar jugador, desasociar
    // jugador, listar

    public void crearEquipo(String nombre, Integer categoria) {
	if (this.equipoRepo.findByNombre(nombre) == null) {
	    Equipo equipo = new EquipoFactory().withNombre(nombre).withCategoria(categoria).build();
	    this.equipoRepo.save(equipo);
	}
    }

    public void borrarEquipo(Integer idEquipo) {
	this.equipoRepo.delete(idEquipo);
    }

    public void asociarCapitanAEquipo(String nombreEquipo, String nroDocumento) throws SQLException {
	Jugador jugador = this.buscarJugador(nroDocumento);
	Equipo equipo = this.buscarEquipo(nombreEquipo);
	if (!equipo.hasCapitan() && equipo.hasJugador(jugador)) {
	    equipo.setCapitan(jugador);
	    this.equipoRepo.update(jugador, equipo.getIdEquipo());
	}
    }

    public void desasociarJugadorDeEquipo(Integer idEquipo, String nroDocumento) throws SQLException {
	Jugador jugador = this.buscarJugador(nroDocumento);
	Equipo equipo = this.buscarEquipo(idEquipo);
	if (equipo.hasJugador(jugador)) {
	    equipo.desasociarJugador(jugador);
	    jugador.desasociarEquipo();
	    this.jugadorRepo.removeEquipoJugador(jugador.getNroDocumento());
	}
    }

    public void asociarJugadorAEquipo(Integer idEquipo, String nroDocumento) throws SQLException {
	Jugador jugador = this.buscarJugador(nroDocumento);
	Equipo equipo = this.buscarEquipo(idEquipo);
	if (equipo.noEstaCompleto() && !jugador.tieneEquipo()) {
	    jugador.setEquipo(equipo);
	    equipo.asignarJugador(jugador);
	    this.jugadorRepo.update(jugador.getNroDocumento(), equipo);
	}
    }

    public Iterable<Equipo> listarEquipos() {
	return this.equipoRepo.findAll();
    }

    // Jugador: Crear, Borrar, listar
    public void crearJugador(String nroDocumento, Integer categoria, String mail, Date fechaNacimiento, String nombre,
	    String telefono) {
	if (!this.existeJugador(nroDocumento)) {
	    Jugador jugador = new JugadorFactory().withNroDocumento(nroDocumento).withCategoria(categoria)
		    .withMail(mail).withFechaNacimiento(fechaNacimiento).withNombre(nombre).withTelefono(telefono)
		    .build();
	    this.jugadorRepo.save(jugador);
	}
    }

    public void borrarJugador(String nroDocumento) {
	this.jugadorRepo.delete(nroDocumento);
    }

    public Iterable<Jugador> listarJugadores() {
	return this.jugadorRepo.findAll();
    }

    // Torneo: crear, existeTorneo, agregar equipo, agregar canchas, agregar
    // arbitros, listarTorneos

    public void crearTorneo(String nombre, Date fechaInicio, String descripcion, Integer categoria) {
	if (!this.existeTorneo(nombre)) {
	    Torneo torneo = new Torneo(nombre, fechaInicio, descripcion, categoria);
	    this.torneoRepo.save(torneo);
	}
    }

    public boolean existeTorneo(String nombreTorneo) {
	return this.torneoRepo.findByNombre(nombreTorneo) != null;
    }

    public void asignarEquipoATorneo(Integer idEquipo, Integer idTorneo) throws SQLException {
	Torneo torneo = this.buscarTorneo(idTorneo);
	Equipo equipo = this.buscarEquipo(idEquipo);

	if (torneo.equipoAgregable(equipo)) {
	    torneo.agregarEquipoATorneo(equipo);
	} // TODO: update equipo?
    }

    public void asignarCanchaATorneo(String nombreCancha, Integer idTorneo) throws SQLException {
	Torneo torneo = this.buscarTorneo(idTorneo);
	Cancha cancha = this.buscarCancha(nombreCancha);
	torneo.agregarCanchaATorneo(cancha);
	// TODO: ManyToMany??
    }

    public Iterable<Torneo> listarTorneos() {
	return this.torneoRepo.findAll();
    }

    public void publicarTorneo(Integer idTorneo) throws SQLException {
	Torneo torneo = this.buscarTorneo(idTorneo);
	if (torneo.publicable()) {
	    List<Partido> partidos = torneo.publicar();
	    this.partidoRepo.save(partidos);
	    torneo.iniciarTorneo();
	}
    }

    public Iterable<Partido> listarPartidos(Integer idTorneo) {
	return this.partidoRepo.findByTorneo(idTorneo);
    }

    public void cargarGoles(Integer cantidadGoles, Integer idPartido, String nroDocumento) throws SQLException {
	Partido partido = this.buscarPartido(idPartido);
	Jugador jugador = this.buscarJugador(nroDocumento);
	for (int i = 0; i < cantidadGoles; i++) {
	    this.golRepo.save(new GolFactory().withPartido(partido).withJugador(jugador).build());
	}
    }

    public void cargarTarjetaRoja(Integer idPartido, String nroDocumento, String tipoTarjeta) throws SQLException {
	Partido partido = this.buscarPartido(idPartido);
	Jugador jugador = this.buscarJugador(nroDocumento);
	this.tarjetaRepo.save(
		new TarjetaFactory().withPartido(partido).withJugador(jugador).withTarjeta(TipoTarjeta.ROJA).build());
    }

    public void cargarTarjetaAmarilla(Integer idPartido, String nroDocumento, String tipoTarjeta) throws SQLException {
	Partido partido = this.buscarPartido(idPartido);
	Jugador jugador = this.buscarJugador(nroDocumento);
	this.tarjetaRepo.save(new TarjetaFactory().withPartido(partido).withJugador(jugador)
		.withTarjeta(TipoTarjeta.AMARILLA).build());
    }

    // estadisticas (goles jugador, tarjetas jugador, goles equipo, tarjetas
    // equipo

    public EstadisticasJugador estadisticasJugador(String nroDocumento) throws SQLException {
	Map<String, Integer> tarjetas = this.tarjetasJugador(nroDocumento);
	return new EstadisticasJugador(this.golesJugador(nroDocumento), tarjetas.get("Tarjetas Rojas"),
		tarjetas.get("Tarjetas Amarillas"), this.jugadorRepo.findOne(nroDocumento).getNombre());
    }

    public EstadisticasEquipo estadisticasEquipo(Integer idEquipo) throws SQLException {

	List<EstadisticasJugador> estadisticas = new ArrayList<EstadisticasJugador>();
	Equipo equipo = this.buscarEquipo(idEquipo);
	equipo.getJugadores().forEach(jugador -> {
	    try {
		estadisticas.add(this.estadisticasJugador(jugador.getNroDocumento()));
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	});
	Integer partidosGanados = this.partidoRepo.countByEquipoAAndResultado(equipo.getIdEquipo(), Resultado.GANA_A)
		+ this.partidoRepo.countByEquipoBAndResultado(equipo.getIdEquipo(), Resultado.GANA_B);
	Integer partidosEmpatados = this.partidoRepo.countByEquipoAAndResultado(equipo.getIdEquipo(), Resultado.EMPATE)
		+ this.partidoRepo.countByEquipoBAndResultado(equipo.getIdEquipo(), Resultado.EMPATE);
	Integer partidosPerdidos = this.partidoRepo.countByEquipoAAndResultado(equipo.getIdEquipo(), Resultado.GANA_B)
		+ this.partidoRepo.countByEquipoBAndResultado(equipo.getIdEquipo(), Resultado.GANA_A);
	return new EstadisticasEquipo(estadisticas, partidosGanados, partidosEmpatados, partidosPerdidos);
    }

    public EstadisticasEquipo estadisticasEquipo(Integer idEquipo, Integer idTorneo) throws SQLException {

	List<EstadisticasJugador> estadisticas = new ArrayList<EstadisticasJugador>();
	Equipo equipo = this.buscarEquipo(idEquipo);
	equipo.getJugadores().forEach(jugador -> {
	    try {
		estadisticas.add(this.estadisticasJugador(jugador.getNroDocumento()));
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	});
	Integer partidosGanados = this.partidoRepo.countByEquipoAAndResultadoAndTorneo(equipo.getIdEquipo(),
		Resultado.GANA_A, idTorneo)
		+ this.partidoRepo.countByEquipoBAndResultadoAndTorneo(equipo.getIdEquipo(), Resultado.GANA_B,
			idTorneo);
	Integer partidosEmpatados = this.partidoRepo.countByEquipoAAndResultadoAndTorneo(equipo.getIdEquipo(),
		Resultado.EMPATE, idTorneo)
		+ this.partidoRepo.countByEquipoBAndResultadoAndTorneo(equipo.getIdEquipo(), Resultado.EMPATE,
			idTorneo);
	Integer partidosPerdidos = this.partidoRepo.countByEquipoAAndResultadoAndTorneo(equipo.getIdEquipo(),
		Resultado.GANA_B, idTorneo)
		+ this.partidoRepo.countByEquipoBAndResultadoAndTorneo(equipo.getIdEquipo(), Resultado.GANA_A,
			idTorneo);
	return new EstadisticasEquipo(estadisticas, partidosGanados, partidosEmpatados, partidosPerdidos);
    }

    public EstadisticasPartido estadisticasPartido(Integer idPartido) throws SQLException {
	Partido partido = this.buscarPartido(idPartido);
	Equipo equipoA = partido.getEquipoA();
	Equipo equipoB = partido.getEquipoB();
	List<EstadisticasJugador> estadisticasA = new ArrayList<>();
	List<EstadisticasJugador> estadisticasB = new ArrayList<>();
	equipoA.getJugadores().forEach(jugador -> {
	    Map<String, Integer> tarjetas = new HashMap<>();
	    try {
		tarjetas = this.tarjetasJugador(jugador.getNroDocumento());
	    } catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	    try {
		estadisticasA.add(new EstadisticasJugador(this.golesJugador(jugador.getNroDocumento()),
			tarjetas.get("Tarjetas Rojas"), tarjetas.get("Tarjetas Amarillas"), jugador.getNombre()));
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	});
	equipoB.getJugadores().forEach(jugador -> {
	    Map<String, Integer> tarjetas = new HashMap<>();
	    try {
		tarjetas = this.tarjetasJugador(jugador.getNroDocumento());
	    } catch (Exception e1) {
		e1.printStackTrace();
	    }
	    try {
		estadisticasB.add(new EstadisticasJugador(this.golesJugador(jugador.getNroDocumento()),
			tarjetas.get("Tarjetas Rojas"), tarjetas.get("Tarjetas Amarillas"), jugador.getNombre()));
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	});
	return new EstadisticasPartido(estadisticasA, estadisticasB, equipoA.getNombre(), equipoB.getNombre(),
		partido.getResultado());
    }

    public TablaGeneral tablaGeneral(Integer idTorneo) throws SQLException {
	Torneo torneo = this.buscarTorneo(idTorneo);
	List<EstadisticasEquipo> metricasEquipos = new ArrayList<>();
	torneo.getEquipos().forEach(equipo -> {
	    try {
		metricasEquipos.add(this.estadisticasEquipo(equipo.getIdEquipo(), idTorneo));
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	});
	return new TablaGeneral(torneo.getNombre(), metricasEquipos);
    }

    private boolean existeJugador(String nroDocumento) {
	return this.jugadorRepo.findOne(nroDocumento) != null;
    }

    private Cancha buscarCancha(String nombre) {
	return this.canchaRepo.findByNombre(nombre);
    }

    private Jugador buscarJugador(String nroDocumento) throws SQLException {
	Jugador jugador = this.jugadorRepo.findOne(nroDocumento);
	if (jugador != null) {
	    return jugador;
	}
	throw new SQLException("El Jugador no existe");
    }

    private Equipo buscarEquipo(Integer idEquipo) throws SQLException {
	Equipo equipo = this.equipoRepo.findOne(idEquipo);
	if (equipo != null) {
	    return equipo;
	}
	throw new SQLException("El Equipo no existe");
    }

    private Equipo buscarEquipo(String nombre) throws SQLException {
	Equipo equipo = this.equipoRepo.findByNombre(nombre);
	if (equipo != null) {
	    return equipo;
	}
	throw new SQLException("El Equipo no existe");
    }

    private Torneo buscarTorneo(Integer idTorneo) throws SQLException {
	Torneo torneo = this.torneoRepo.findOne(idTorneo);
	if (torneo != null) {
	    return torneo;
	}
	throw new SQLException("El Torneo no existe");
    }

    private Integer golesJugador(String nroDocumento) throws SQLException {
	Jugador jugador = this.buscarJugador(nroDocumento);
	return this.golRepo.countByJugador(jugador);
    }

    private Map<String, Integer> tarjetasJugador(String nroDocumento) throws SQLException {
	Jugador jugador = this.buscarJugador(nroDocumento);
	Map<String, Integer> tarjetas = new HashMap<String, Integer>();
	tarjetas.put("Tarjetas Amarillas", this.tarjetaRepo.countByJugadorAndTipo(jugador, TipoTarjeta.AMARILLA));
	tarjetas.put("Tarjetas Rojas", this.tarjetaRepo.countByJugadorAndTipo(jugador, TipoTarjeta.ROJA));
	return tarjetas;
    }

    private Partido buscarPartido(Integer idPartido) throws SQLException {
	Partido partido = this.partidoRepo.findOne(idPartido);
	if (partido != null) {
	    return partido;
	}
	throw new SQLException("El partido no existe");
    }

}
