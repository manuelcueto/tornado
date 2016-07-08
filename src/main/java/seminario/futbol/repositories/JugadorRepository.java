package seminario.futbol.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Jugador;

public interface JugadorRepository extends CrudRepository<Jugador, String> {

	 @Modifying
	    @Query("update jugadores set equipo = ?1 where jugadores.nroDocumento =?2")
		public void saveEquipoJugador(Integer idEquipo, String nroDocumento);

	 @Modifying
	    @Query("update jugadores set equipo = null where jugadores.nroDocumento =?1")
	 	public void removeEquipoJugador(String nroDocumento);
}
