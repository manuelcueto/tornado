package seminario.futbol.repositories;

import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Torneo;

public interface TorneoRepository extends CrudRepository<Torneo, Integer> {

    public Torneo findByNombre(String nombre);
}