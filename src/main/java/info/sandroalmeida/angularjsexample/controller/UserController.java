package info.sandroalmeida.angularjsexample.controller;

import info.sandroalmeida.angularjsexample.dto.UserDTO;
import info.sandroalmeida.angularjsexample.error.CustomErrortype;
import info.sandroalmeida.angularjsexample.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
        logger.debug("listAllUsers called");
        List<UserDTO> users = (List) userJpaRepository.findAll();
        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody final UserDTO user){
        logger.debug("createUser called");
        if(userJpaRepository.findByName(user.getName()) != null){
            return new ResponseEntity<>(
                    new CustomErrortype("Unable to create new user. A user with name " +
                            user.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        userJpaRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") final Long id){
        logger.debug("findbyId called");
        Optional<UserDTO> optUser = userJpaRepository.findById(id);
        if(!optUser.isPresent()){
            return new ResponseEntity<>(
                    new CustomErrortype("User with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        UserDTO user = optUser.get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") final Long id, @RequestBody UserDTO user) {
        logger.debug("updateUser called");
        Optional<UserDTO> optUser = userJpaRepository.findById(id);
        if(!optUser.isPresent()){
            return new ResponseEntity<>(new CustomErrortype("Unable to update. User with id " +
                    id + " not found."), HttpStatus.NOT_FOUND);
        }
        UserDTO currentUser = optUser.get();
        currentUser.setName(user.getName());
        currentUser.setAddress(user.getAddress());
        currentUser.setEmail(user.getEmail());
        currentUser = userJpaRepository.save(currentUser);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") final Long id) {
        logger.debug("deleteUser called");
        Optional<UserDTO> optUser = userJpaRepository.findById(id);
        if(!optUser.isPresent()){
            return new ResponseEntity<>(new CustomErrortype("Unable to delete. user with id " +
                    id + " not found."), HttpStatus.NOT_FOUND);
        }
        userJpaRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
