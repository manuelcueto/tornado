package seminario.futbol.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Equipo;
import seminario.futbol.model.Jugador;

public interface EquipoRepository extends CrudRepository<Equipo, Integer> {

    public Equipo findByNombre(String nombre);

    @Modifying
    @Query("update Equipo e set e.capitan=?1 where e.idEquipo=?2")
    public void update(Jugador capitan, Integer idEquipo);

    @Modifying
    @Query("update Equipo e set e.asignado=?2 where e.idEquipo=?1")
    public void updateAsignado(Integer idEquipo, boolean asignado);

}