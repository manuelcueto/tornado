package seminario.futbol.repositories;

import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Equipo;

public interface EquipoRepository extends CrudRepository<Equipo, Integer> {

    public Equipo findByNombre(String nombre);

    // @Modifying
    // @Query("update equipos set capitan=? where idEquipo=?")
    // public void saveCapitanDeEquipo(String nroDocumento, Integer idEquipo);
}