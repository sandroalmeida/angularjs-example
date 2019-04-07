package info.sandroalmeida.angularjsexample.repository;

import info.sandroalmeida.angularjsexample.dto.UserDTO;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by sandro on 07/04/19
 */
public interface UserJpaRepository extends CrudRepository<UserDTO, Long> {

    UserDTO findByName(String name);

}
