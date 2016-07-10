package seminario.futbol.repositories;

import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Arbitro;

public interface ArbitroRepository extends CrudRepository<Arbitro, Integer> {

    public Arbitro findByNombre(String nombre);
}
