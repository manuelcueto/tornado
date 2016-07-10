package seminario.futbol.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import seminario.futbol.model.Cancha;
import seminario.futbol.model.Estado;

public interface CanchaRepository extends CrudRepository<Cancha, Integer> {

    @Modifying
    @Query("update Cancha c set c.dueno=?1, c.telefono=?2, c.nombre=?3, c.direccion=?4 where c.idCancha = ?5")
    public int modificarCancha(String dueno, String telefono, String nombre, String direccion, Integer idCancha);

    public Cancha findByNombre(String nombre);

    @Modifying
    @Query("update Cancha c set c.estado = ?2 where c.idCancha = ?1")
    public void bajaLogica(Integer idCancha, Estado estado);

    public Iterable<Cancha> findAllByEstado(Estado estado);
}
