package seminario.futbol.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Equipo;
import seminario.futbol.model.Jugador;

public interface JugadorRepository extends CrudRepository<Jugador, String> {

    public Jugador findByNroDocumentoAndEquipoIsNull(String nroDocumento);

    @Modifying
    @Query("update Jugador j set j.equipo = null where j.nroDocumento =?1")
    public void removeEquipoJugador(String nroDocumento);

    @Modifying
    @Query("update Jugador j set j.equipo = ?2 where j.nroDocumento =?1")
    public void update(String nroDocumento, Equipo equipo);

    public Iterable<Jugador> findByEquipo(Equipo equipo);

    public Iterable<Jugador> findByEquipoIsNull();

}
