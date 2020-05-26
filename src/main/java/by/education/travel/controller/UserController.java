package by.education.travel.controller;

import by.education.travel.entity.User;
import by.education.travel.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user/")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        log.info("In getUserById {} user: {}", id, user);
        if (user == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        log.info("In getAllUsers find {}", users);
        if (users == null || users.isEmpty()) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(users, OK);
    }

    @PostMapping("")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        log.info("In saveUser {}", user);
        if (user == null) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        userService.saveUser(user);
        return new ResponseEntity<>(user, CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        userService.deleteUser(user);
        log.info("In deleteUser {} successfully deleted", user);
        return new ResponseEntity<>(user, NO_CONTENT);
    }

    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (user == null || userService.getUserById(user.getId()) == null) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        userService.updateUser(user);
        log.info("In updateUser {} successfully updated", user);
        return new ResponseEntity<>(user, OK);
    }
}
