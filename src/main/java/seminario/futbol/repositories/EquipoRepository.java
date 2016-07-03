package seminario.futbol.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Equipo;

public interface EquipoRepository extends CrudRepository<Equipo, Integer> {

    public Equipo findByNombre(String nombre);
   
    @Query("select count(*) from equipo_jugador where nroDocumento= ?1")
	public int findByNroDocumento(String nroDocumento);

    @Modifying
    @Query("insert into equipo_jugador values (?1, ?2) ")
	public void saveJugadorEquipo(String nroDocumento, Integer idEquipo);
    
}