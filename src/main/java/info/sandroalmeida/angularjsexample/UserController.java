package info.sandroalmeida.angularjsexample;

import info.sandroalmeida.angularjsexample.dto.UserDTO;
import info.sandroalmeida.angularjsexample.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sandro on 07/04/19
 */

@RestController
@RequestMapping("/api/user")
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserJpaRepository userJpaRepository;

    public UserController(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> listAllUsers(){
        logger.debug("listAllusers called");
        List<UserDTO> users = (List) userJpaRepository.findAll();
        return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@RequestBody final UserDTO user){
        logger.debug("createUser called");
        userJpaRepository.save(user);
        return new ResponseEntity<UserDTO>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") final Long id){
        logger.debug("findbyId called");
        UserDTO user = userJpaRepository.findById(id).get();
        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") final Long id, @RequestBody UserDTO user) {
        logger.debug("updateUser called");
        UserDTO currentUser = userJpaRepository.findById(id).get();
        currentUser.setName(user.getName());
        currentUser.setAddress(user.getAddress());
        currentUser.setEmail(user.getEmail());
        currentUser = userJpaRepository.save(currentUser);
        return new ResponseEntity<UserDTO>(currentUser, HttpStatus.OK);
    }
}
