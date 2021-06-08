package lecturekej;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel="rooms", path="rooms")
public interface RoomRepository extends CrudRepository<Room, Long>{

    List<Room> findByLecturenameIsNull();
}
