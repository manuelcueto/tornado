package seminario.futbol.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.EstadoTorneo;
import seminario.futbol.model.Torneo;

public interface TorneoRepository extends CrudRepository<Torneo, Integer> {

    public Torneo findByNombre(String nombre);

    @Modifying
    @Query("update Torneo t set t.estado=?2 where t.idTorneo = ?1")
    public void iniciarTorneo(Integer idTorneo, EstadoTorneo estado);
}