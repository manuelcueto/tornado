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
import seminario.futbol.model.Arbitro;
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
    public void crearCancha(@RequestBody Cancha cancha) {
	this.tornadoService.crearCancha(cancha);
    }

    @RequestMapping(value = "/canchas/{nombre}", method = RequestMethod.GET)
    public Response<Boolean> existeCancha(@PathVariable("nombre") String nombre) {
	return new Response<Boolean>(this.tornadoService.existeCancha(nombre));
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
    public Response<Iterable<Cancha>> listarCanchas() {
	return new Response<Iterable<Cancha>>(this.tornadoService.listarCanchas());
    }

    @RequestMapping(value = "/arbitros", method = RequestMethod.PUT)
    public void crearArbitro(@RequestBody Arbitro arbitro) {
	this.tornadoService.agregarArbitro(arbitro);
    }

    @RequestMapping(value = "/arbitros/{nombre}", method = RequestMethod.GET)
    public Response<Boolean> existeArbitro(@PathVariable("nombre") String nombre) {
	return new Response<Boolean>(this.tornadoService.existeArbitro(nombre));
    }

    @RequestMapping(value = "/arbitros", method = RequestMethod.GET)
    public Response<Iterable<Arbitro>> listarArbitros() {
	return new Response<Iterable<Arbitro>>(this.tornadoService.listarArbitros());
    }

    @RequestMapping(value = "/arbitros", method = RequestMethod.POST)
    public void borrarArbitro(@RequestBody Arbitro arbitro) {
	this.tornadoService.borrarArbitro(arbitro);
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

    @RequestMapping(value = "/equipos/{idEquipo}/jugadores", method = RequestMethod.POST)
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
    public Response<Iterable<Equipo>> listarEquipos() {
	return new Response<Iterable<Equipo>>(this.tornadoService.listarEquipos());
    }

    @RequestMapping(value = "/jugadores", method = RequestMethod.PUT)
    public void crearJugador(@RequestBody Jugador jugador) throws ParseException {

	this.tornadoService.crearJugador(jugador);
    }

    @RequestMapping(value = "/jugadores/{nroDocumento}", method = RequestMethod.GET)
    public Response<Boolean> existeJugador(@PathVariable("nroDocumento") String nroDocumento) {
	return new Response<Boolean>(this.tornadoService.existeJugador(nroDocumento));
    }

    @RequestMapping(value = "/jugadores", method = RequestMethod.POST)
    public void borrarJugador(@RequestBody Jugador jugador) {
	this.tornadoService.borrarJugador(jugador.getNroDocumento());
    }

    @RequestMapping(value = "/jugadores", method = RequestMethod.GET)
    public Response<Iterable<Jugador>> listarJugadores() {
	return new Response<Iterable<Jugador>>(this.tornadoService.listarJugadores());
    }

    @RequestMapping(value = "/equipos/{idEquipo}/jugadores", method = RequestMethod.GET)
    public Response<Iterable<Jugador>> listarJugadores(@PathVariable("idEquipo") Integer idEquipo) throws SQLException {
	return new Response<Iterable<Jugador>>(this.tornadoService.listarJugadores(idEquipo));
    }

    @RequestMapping(value = "/equipos/jugadores", method = RequestMethod.GET)
    public Response<Iterable<Jugador>> listarJugadoresSinEquipo() throws SQLException {
	return new Response<Iterable<Jugador>>(this.tornadoService.listarJugadoresSinEquipo());
    }

    @RequestMapping(value = "/torneos", method = RequestMethod.POST)
    public void crearTorneo(@RequestParam String nombre, @RequestParam String fechaInicio,
	    @RequestParam String descripcion, @RequestParam Integer categoria) throws ParseException {
	this.tornadoService.crearTorneo(nombre, this.parseDate(fechaInicio), descripcion, categoria);
    }

    @RequestMapping(value = "/torneos/{nombre}", method = RequestMethod.GET)
    public Response<Boolean> existeTorneo(@PathVariable("nombre") String nombre) {
	return new Response<Boolean>(this.tornadoService.existeTorneo(nombre));
    }

    @RequestMapping(value = "/torneos/{idTorneo}/equipos", method = RequestMethod.POST)
    public void asignarEquipoATorneo(@PathVariable("idTorneo") Integer idTorneo, @RequestBody Equipo equipo)
	    throws SQLException {
	this.tornadoService.asignarEquipoATorneo(equipo.getIdEquipo(), idTorneo);
    }

    @RequestMapping(value = "/torneos", method = RequestMethod.GET)
    public Response<Iterable<Torneo>> listarTorneos() {
	return new Response<Iterable<Torneo>>(this.tornadoService.listarTorneos());
    }

    @RequestMapping(value = "/torneos/{idTorneo}", method = RequestMethod.POST)
    public void publicarTorneo(@PathVariable("idTorneo") Integer idTorneo) throws SQLException {
	this.tornadoService.publicarTorneo(idTorneo);
    }

    @RequestMapping(value = "/torneos/{idTorneo}/partidos", method = RequestMethod.GET)
    public Response<Iterable<Partido>> listarPartidos(@PathVariable("idTorneo") Integer idTorneo) {
	return new Response<Iterable<Partido>>(this.tornadoService.listarPartidos(idTorneo));
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

    @RequestMapping(value = "/partidos/{idPartido}", method = RequestMethod.POST)
    public void jugarPartido(@PathVariable("idPartido") Integer idPartido) {
	this.tornadoService.jugarPartido(idPartido);
    }

    @RequestMapping(value = "/estadisticas/jugadores/{nroDocumento}", method = RequestMethod.POST)
    public Response<EstadisticasJugador> obtenerEstadisticasJugador(@PathVariable("nroDocumento") String nroDocumento)
	    throws SQLException {
	return new Response<EstadisticasJugador>(this.tornadoService.estadisticasJugador(nroDocumento));
    }

    @RequestMapping(value = "/estadisticas/equipos", method = RequestMethod.POST)
    public Response<EstadisticasEquipo> obtenerEstadisticasEquipo(@RequestBody Equipo equipo) throws SQLException {
	return new Response<EstadisticasEquipo>(this.tornadoService.estadisticasEquipo(equipo.getIdEquipo()));
    }

    @RequestMapping(value = "/estadisticas/partidos", method = RequestMethod.POST)
    public Response<EstadisticasPartido> obtenerEstadisticasPartido(@RequestBody Partido partido) throws SQLException {
	return new Response<EstadisticasPartido>(this.tornadoService.estadisticasPartido(partido.getIdPartido()));
    }

    @RequestMapping(value = "/estadisticas/torneos", method = RequestMethod.POST)
    public Response<TablaGeneral> obtenerTablaGeneral(@RequestBody Torneo torneo) throws SQLException {
	return new Response<TablaGeneral>(this.tornadoService.tablaGeneral(torneo.getIdTorneo()));
    }

    private Date parseDate(String dateAsString) throws ParseException {
	return DateUtils.parseDate(dateAsString, "dd-MM-yyyy");
    }
}
