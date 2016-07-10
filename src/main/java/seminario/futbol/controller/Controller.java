package seminario.futbol.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import seminario.futbol.estadisticas.EstadisticasEquipo;
import seminario.futbol.estadisticas.EstadisticasJugador;
import seminario.futbol.estadisticas.EstadisticasPartido;
import seminario.futbol.estadisticas.TablaGeneral;
import seminario.futbol.model.Cancha;
import seminario.futbol.model.Equipo;
import seminario.futbol.model.Jugador;
import seminario.futbol.model.Partido;
import seminario.futbol.model.Tarjeta;
import seminario.futbol.model.TipoTarjeta;
import seminario.futbol.model.Torneo;
import seminario.futbol.service.TornadoService;

@RestController
public class Controller {

    @Autowired
    private TornadoService tornadoService;

    @RequestMapping(value = "/canchas", method = RequestMethod.POST)
    public void crearCancha(@RequestParam String nombre, @RequestParam String telefono, @RequestParam String dueno,
	    @RequestParam String direccion) {
	this.tornadoService.crearCancha(nombre, direccion, dueno, telefono);
    }

    @RequestMapping(value = "/canchas/{idCancha}", method = RequestMethod.PUT)
    public void modificarCancha(@PathVariable("idCancha") Integer idCancha, @RequestBody Cancha cancha) {
	this.tornadoService.modificarCancha(idCancha, cancha);
    }

    @RequestMapping(value = "/canchas/{idCancha}", method = RequestMethod.DELETE)
    public void borrarCancha(@PathVariable("idCancha") Integer idCancha) {
	this.tornadoService.borrarCancha(idCancha);
    }

    @RequestMapping(value = "/canchas", method = RequestMethod.GET)
    public Iterable<Cancha> listarCanchas() {
	return this.tornadoService.listarCanchas();
    }

    @RequestMapping(value = "/equipos", method = RequestMethod.PUT)
    public void crearEquipo(@RequestParam String nombre, @RequestParam Integer categoria) {
	this.tornadoService.crearEquipo(nombre, categoria);
    }

    @RequestMapping(value = "/equipos/{idEquipo}", method = RequestMethod.DELETE)
    public void borrarEquipo(@PathVariable("idEquipo") Integer idEquipo) {
	this.tornadoService.borrarEquipo(idEquipo);
    }

    @RequestMapping(value = "/equipos/{nombreEquipo}/capitan", method = RequestMethod.PUT)
    public void asignarCapitanAEquipo(@PathVariable("nombreEquipo") String nombreEquipo, @RequestBody Jugador jugador) {
	try {
	    this.tornadoService.asociarCapitanAEquipo(nombreEquipo, jugador.getNroDocumento());
	} catch (SQLException e) {
	    throw new IllegalStateException(e.getMessage());
	}
    }

    @RequestMapping(value = "/equipos/{idEquipo}/jugadores", method = RequestMethod.DELETE)
    public void desasociarJugadorDeEquipo(@PathVariable("idEquipo") Integer idEquipo, @RequestBody Jugador jugador) {
	try {
	    this.tornadoService.desasociarJugadorDeEquipo(idEquipo, jugador.getNroDocumento());
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping(value = "/equipos/{idEquipo}/jugadores", method = RequestMethod.PUT)
    public void asociarJugadorAEquipo(@PathVariable("idEquipo") Integer idEquipo, @RequestBody Jugador jugador) {
	try {
	    this.tornadoService.asociarJugadorAEquipo(idEquipo, jugador.getNroDocumento());
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping(value = "/equipos", method = RequestMethod.GET)
    public Iterable<Equipo> listarEquipos() {
	return this.tornadoService.listarEquipos();
    }

    @RequestMapping(value = "/jugadores", method = RequestMethod.PUT)
    public void crearJugador(@RequestParam("nroDocumento") String nroDocumento,
	    @RequestParam("categoria") Integer categoria, @RequestParam("mail") String mail,
	    @RequestParam("fechaNacimiento") String fechaNacimiento, @RequestParam("nombre") String nombre,
	    @RequestParam("telefono") String telefono) throws ParseException {

	this.tornadoService.crearJugador(nroDocumento, categoria, mail, this.parseDate(fechaNacimiento), nombre,
		telefono);
    }

    @RequestMapping(value = "/jugadores", method = RequestMethod.DELETE)
    public void borrarJugador(@RequestBody Jugador jugador) {
	this.tornadoService.borrarJugador(jugador.getNroDocumento());
    }

    @RequestMapping(value = "/jugadores", method = RequestMethod.GET)
    public Iterable<Jugador> listarJugadores() {
	return this.tornadoService.listarJugadores();
    }

    @RequestMapping(value = "/torneos", method = RequestMethod.POST)
    public void crearTorneo(@RequestParam String nombre, @RequestParam String fechaInicio,
	    @RequestParam String descripcion, @RequestParam Integer categoria) throws ParseException {
	this.tornadoService.crearTorneo(nombre, this.parseDate(fechaInicio), descripcion, categoria);
    }

    @RequestMapping(value = "/torneos/{nombre}", method = RequestMethod.GET)
    public boolean existeTorneo(@PathVariable("nombre") String nombre) {
	return this.tornadoService.existeTorneo(nombre);
    }

    @RequestMapping(value = "/torneos/{idTorneo}/equipos", method = RequestMethod.POST)
    public void asignarEquipoATorneo(@PathVariable("idTorneo") Integer idTorneo, @RequestBody Equipo equipo)
	    throws SQLException {
	this.tornadoService.asignarEquipoATorneo(equipo.getIdEquipo(), idTorneo);
    }

    @RequestMapping(value = "/torneos/{idTorneo}/canchas", method = RequestMethod.POST)
    public void asignarCanchaATorneo(@PathVariable("idTorneo") Integer idTorneo, @RequestBody Cancha cancha)
	    throws SQLException {
	this.tornadoService.asignarCanchaATorneo(cancha.getNombre(), idTorneo);
    }

    @RequestMapping(value = "/torneos", method = RequestMethod.GET)
    public Iterable<Torneo> listarTorneos() {
	return this.tornadoService.listarTorneos();
    }

    @RequestMapping(value = "/torneos/{idTorneo}", method = RequestMethod.POST)
    public void publicarTorneo(@PathVariable("idTorneo") Integer idTorneo) throws SQLException {
	this.tornadoService.publicarTorneo(idTorneo);
    }

    @RequestMapping(value = "/torneos/{idTorneo}/partidos/", method = RequestMethod.GET)
    public Iterable<Partido> listarPartidos(@PathVariable("idTorneo") Integer idTorneo) {
	return this.tornadoService.listarPartidos(idTorneo);
    }

    @RequestMapping(value = "/partidos/{idPartido}/jugadores/{nroDocumento}", method = RequestMethod.POST)
    public void cargarGol(@PathVariable("idPartido") Integer idPartido,
	    @PathVariable("nroDocumento") String nroDocumento, @RequestParam("cantidadGoles") Integer cantidadGoles)
	    throws SQLException {
	this.tornadoService.cargarGoles(cantidadGoles, idPartido, nroDocumento);
    }

    @RequestMapping(value = "/tarjetas", method = RequestMethod.PUT)
    public void cargarTarjeta(@RequestBody Tarjeta tarjeta) throws SQLException {
	if (tarjeta.getTipo() == TipoTarjeta.AMARILLA) {
	    this.tornadoService.cargarTarjetaAmarilla(tarjeta.getPartido().getIdPartido(),
		    tarjeta.getJugador().getNroDocumento(), tarjeta.getTipo());
	} else {
	    this.tornadoService.cargarTarjetaRoja(tarjeta.getPartido().getIdPartido(),
		    tarjeta.getJugador().getNroDocumento(), tarjeta.getTipo());
	}
    }

    @RequestMapping(value = "/estadisticas/jugadores", method = RequestMethod.GET)
    public EstadisticasJugador obtenerEstadisticasJugador(@RequestBody Jugador jugador) throws SQLException {
	return this.tornadoService.estadisticasJugador(jugador.getNroDocumento());
    }

    @RequestMapping(value = "/estadisticas/equipos", method = RequestMethod.GET)
    public EstadisticasEquipo obtenerEstadisticasEquipo(@RequestBody Equipo equipo) throws SQLException {
	return this.tornadoService.estadisticasEquipo(equipo.getIdEquipo());
    }

    @RequestMapping(value = "/estadisticas/partidos", method = RequestMethod.GET)
    public EstadisticasPartido obtenerEstadisticasPartido(@RequestBody Partido partido) throws SQLException {
	return this.tornadoService.estadisticasPartido(partido.getIdPartido());
    }

    @RequestMapping(value = "/estadisticas/torneos", method = RequestMethod.GET)
    public TablaGeneral obtenerTablaGeneral(@RequestBody Torneo torneo) throws SQLException {
	return this.tornadoService.tablaGeneral(torneo.getIdTorneo());
    }

    private Date parseDate(String dateAsString) throws ParseException {
	return DateUtils.parseDate(dateAsString, "dd-MM-yyyy");
    }
}
