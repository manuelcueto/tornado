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
import seminario.futbol.model.Arbitro;
import seminario.futbol.model.Cancha;
import seminario.futbol.model.Equipo;
import seminario.futbol.model.Estado;
import seminario.futbol.model.EstadoJugador;
import seminario.futbol.model.Jugador;
import seminario.futbol.model.Partido;
import seminario.futbol.model.Resultado;
import seminario.futbol.model.TipoTarjeta;
import seminario.futbol.model.Torneo;
import seminario.futbol.model.factories.EquipoFactory;
import seminario.futbol.model.factories.GolFactory;
import seminario.futbol.model.factories.TarjetaFactory;
import seminario.futbol.model.factories.TorneoFactory;
import seminario.futbol.repositories.ArbitroRepository;
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
	@Autowired
	private ArbitroRepository arbitroRepo;

	// canchas: crear, modificar, borrar, listar

	public void crearCancha(Cancha cancha) {
		cancha.setEstado(Estado.ACTIVO);
		this.canchaRepo.save(cancha);
	}

	public boolean existeCancha(String nombre) {
		return this.canchaRepo.findByNombre(nombre) != null;
	}

	public void modificarCancha(Integer idCancha, Cancha cancha) {
		this.canchaRepo.modificarCancha(cancha.getDueno(), cancha.getTelefono(), cancha.getNombre(),
				cancha.getDireccion(), idCancha);
	}

	public void borrarCancha(Integer idCancha) {
		this.canchaRepo.bajaLogica(idCancha, Estado.INACTIVO);
	}

	public Iterable<Cancha> listarCanchas() {
		return this.canchaRepo.findAllByEstado(Estado.ACTIVO);
	}

	// Equipos: Crear, borrar, elegir capitan, asociar jugador, desasociar
	// jugador, listar

	public void crearEquipo(String nombre, Integer categoria) {
		if (this.equipoRepo.findByNombre(nombre) == null) {
			Equipo equipo = new EquipoFactory().withNombre(nombre).withCategoria(categoria).build();
			this.equipoRepo.save(equipo);
		} else {
			throw new IllegalArgumentException("Ya existe equipo con ese nombre");
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
	public void crearJugador(Jugador jugador) {
		jugador.setEstado(EstadoJugador.HABILITADO);
		this.jugadorRepo.save(jugador);
	}

	public boolean existeJugador(String nroDocumento) {
		return this.jugadorRepo.findOne(nroDocumento) != null;
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
			Torneo torneo = new TorneoFactory().withNombre(nombre).withDescripcion(descripcion).withInicio(fechaInicio)
					.withCategoria(categoria).build();
			this.torneoRepo.save(torneo);
		}
	}

	public boolean existeTorneo(String nombreTorneo) {
		Torneo torneo = this.torneoRepo.findByNombre(nombreTorneo);
		return torneo != null;
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
			List<Cancha> canchas = (List<Cancha>) canchaRepo.findAllByEstado(Estado.ACTIVO);
			List<Arbitro> arbitros = (List<Arbitro>) arbitroRepo.findAllByEstado(Estado.ACTIVO);
			if (canchas.size() > 0 && arbitros.size() > 0) {
				List<Partido> partidos = torneo.publicar(canchas, arbitros);
				this.partidoRepo.save(partidos);
				torneo.iniciarTorneo();
			} else {
				throw new IllegalStateException("No hay canchas o arbitros disponibles para iniciar un torneo");
			}
		}
	}

	public Iterable<Jugador> listarJugadores(Integer idEquipo) throws SQLException {
		Equipo equipo = this.buscarEquipo(idEquipo);
		return this.jugadorRepo.findByEquipo(equipo);
	}

	public Iterable<Jugador> listarJugadoresSinEquipo() throws SQLException {
		return this.jugadorRepo.findByEquipoIsNull();
	}

	public Iterable<Partido> listarPartidos(Integer idTorneo) {
		Torneo torneo = this.torneoRepo.findOne(idTorneo);
		return this.partidoRepo.findByTorneo(torneo);
	}

	public void cargarGoles(Integer cantidadGoles, Integer idPartido, String nroDocumento) throws SQLException {
		Partido partido = this.buscarPartido(idPartido);
		Jugador jugador = this.buscarJugador(nroDocumento);
		for (int i = 0; i < cantidadGoles; i++) {
			this.golRepo.save(new GolFactory().withPartido(partido).withJugador(jugador).build());
		}
	}

	public void cargarTarjetaRoja(Integer idPartido, String nroDocumento, TipoTarjeta tipoTarjeta) throws SQLException {
		Partido partido = this.buscarPartido(idPartido);
		Jugador jugador = this.buscarJugador(nroDocumento);
		this.tarjetaRepo.save(
				new TarjetaFactory().withPartido(partido).withJugador(jugador).withTarjeta(TipoTarjeta.ROJA).build());
	}

	public void cargarTarjetaAmarilla(Integer idPartido, String nroDocumento, TipoTarjeta tipoTarjeta)
			throws SQLException {
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
		Integer partidosGanados = this.partidoRepo.countByEquipoAAndResultado(equipo, Resultado.GANA_A)
				+ this.partidoRepo.countByEquipoBAndResultado(equipo, Resultado.GANA_B);
		Integer partidosEmpatados = this.partidoRepo.countByEquipoAAndResultado(equipo, Resultado.EMPATE)
				+ this.partidoRepo.countByEquipoBAndResultado(equipo, Resultado.EMPATE);
		Integer partidosPerdidos = this.partidoRepo.countByEquipoAAndResultado(equipo, Resultado.GANA_B)
				+ this.partidoRepo.countByEquipoBAndResultado(equipo, Resultado.GANA_A);
		return new EstadisticasEquipo(equipo.getNombre(), estadisticas, partidosGanados, partidosEmpatados,
				partidosPerdidos);
	}

	public EstadisticasEquipo estadisticasEquipo(Integer idEquipo, Integer idTorneo) throws SQLException {

		List<EstadisticasJugador> estadisticas = new ArrayList<EstadisticasJugador>();
		Equipo equipo = this.buscarEquipo(idEquipo);
		Torneo torneo = this.buscarTorneo(idTorneo);
		equipo.getJugadores().forEach(jugador -> {
			try {
				estadisticas.add(this.estadisticasJugador(jugador.getNroDocumento()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		Integer partidosGanados = this.partidoRepo.countByEquipoAAndResultadoAndTorneo(equipo, Resultado.GANA_A, torneo)
				+ this.partidoRepo.countByEquipoBAndResultadoAndTorneo(equipo, Resultado.GANA_B, torneo);
		Integer partidosEmpatados = this.partidoRepo.countByEquipoAAndResultadoAndTorneo(equipo, Resultado.EMPATE,
				torneo) + this.partidoRepo.countByEquipoBAndResultadoAndTorneo(equipo, Resultado.EMPATE, torneo);
		Integer partidosPerdidos = this.partidoRepo.countByEquipoAAndResultadoAndTorneo(equipo, Resultado.GANA_B,
				torneo) + this.partidoRepo.countByEquipoBAndResultadoAndTorneo(equipo, Resultado.GANA_A, torneo);
		return new EstadisticasEquipo(equipo.getNombre(), estadisticas, partidosGanados, partidosEmpatados,
				partidosPerdidos);
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
				tarjetas = this.tarjetasJugador(jugador.getNroDocumento(), partido);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				estadisticasA.add(new EstadisticasJugador(this.golesJugador(jugador.getNroDocumento(), partido),
						tarjetas.get("Tarjetas Rojas"), tarjetas.get("Tarjetas Amarillas"), jugador.getNombre()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		equipoB.getJugadores().forEach(jugador -> {
			Map<String, Integer> tarjetas = new HashMap<>();
			try {
				tarjetas = this.tarjetasJugador(jugador.getNroDocumento(), partido);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				estadisticasB.add(new EstadisticasJugador(this.golesJugador(jugador.getNroDocumento(), partido),
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

	public void agregarArbitro(Arbitro arbitro) {
		arbitro.setEstado(Estado.ACTIVO);
		this.arbitroRepo.save(arbitro);
	}

	public boolean existeArbitro(String nombre) {
		return this.arbitroRepo.findByNombre(nombre) != null;
	}

	public void borrarArbitro(Arbitro arbitro) {
		this.arbitroRepo.bajaLogica(arbitro.getIdArbitro(), Estado.INACTIVO);
	}

	public Iterable<Arbitro> listarArbitros() {
		return this.arbitroRepo.findAllByEstado(Estado.ACTIVO);
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

	private Integer golesJugador(String nroDocumento, Partido partido) throws SQLException {
		Jugador jugador = this.buscarJugador(nroDocumento);
		return this.golRepo.countByJugadorAndPartido(jugador, partido);
	}

	private Integer golesJugador(String nroDocumento, Torneo torneo) throws SQLException {
		Jugador jugador = this.buscarJugador(nroDocumento);
		return this.golRepo.countByJugadorAndTorneo(jugador, torneo);
	}

	private Map<String, Integer> tarjetasJugador(String nroDocumento) throws SQLException {
		Jugador jugador = this.buscarJugador(nroDocumento);
		Map<String, Integer> tarjetas = new HashMap<String, Integer>();
		tarjetas.put("Tarjetas Amarillas", this.tarjetaRepo.countByJugadorAndTipo(jugador, TipoTarjeta.AMARILLA));
		tarjetas.put("Tarjetas Rojas", this.tarjetaRepo.countByJugadorAndTipo(jugador, TipoTarjeta.ROJA));
		return tarjetas;
	}

	private Map<String, Integer> tarjetasJugador(String nroDocumento, Partido partido) throws SQLException {
		Jugador jugador = this.buscarJugador(nroDocumento);
		Map<String, Integer> tarjetas = new HashMap<String, Integer>();
		tarjetas.put("Tarjetas Amarillas",
				this.tarjetaRepo.countByJugadorAndTipoAndPartido(jugador, TipoTarjeta.AMARILLA, partido));
		tarjetas.put("Tarjetas Rojas",
				this.tarjetaRepo.countByJugadorAndTipoAndPartido(jugador, TipoTarjeta.ROJA, partido));
		return tarjetas;
	}

	private Partido buscarPartido(Integer idPartido) throws SQLException {
		Partido partido = this.partidoRepo.findOne(idPartido);
		if (partido != null) {
			return partido;
		}
		throw new SQLException("El partido no existe");
	}

	public void jugarPartido(Integer idPartido) {
		Partido partido = this.partidoRepo.findOne(idPartido);
		if (partido != null) {
			this.partidoRepo.updatePartido(idPartido, partido.jugarPartido());
		}
	}

}
