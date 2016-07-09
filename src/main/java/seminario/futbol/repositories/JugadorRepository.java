package seminario.futbol.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Equipo;
import seminario.futbol.model.Jugador;

public interface JugadorRepository extends CrudRepository<Jugador, String> {

    public Jugador findByNroDocumentoAndEquipoIsNull(String nroDocumento);

    // @Modifying
    // @Query("update jugadores j set j.idEquipo = ?1 where j.nroDocumento =
    // ?2")
    // public int setEquipo(Integer idEquipo, String nroDocumento);
    //
    // @Modifying
    // @Query("update jugadores set equipo = ?1 where jugadores.nroDocumento
    // =?2")
    // public void saveEquipoJugador(Integer idEquipo, String nroDocumento);
    //
    @Modifying
    @Query("update Jugador j set j.equipo = null where j.nroDocumento =?")
    public void removeEquipoJugador(String nroDocumento);

    @Modifying
    @Query("update Jugador j set j.equipo = ? where j.nroDocumento =?")
    public void update(String nroDocumento, Equipo equipo);

}
