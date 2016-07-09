package seminario.futbol.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Cancha;

public interface CanchaRepository extends CrudRepository<Cancha, Integer> {

    @Modifying
    @Query("update Cancha c set c.dueno=?, c.telefono=?, c.nombre=?, c.direccion=? where c.idCancha = ?")
    public int modificarCancha(String dueno, String telefono, String nombre, String direccion, Integer idCancha);

    public Cancha findByNombre(String nombre);
}
