package com.example.proyectodws;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/api")
@RestController
public class UserRestController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<Collection> userList() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if (user != null) {
            userService.addUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/user/{user}")
    public ResponseEntity<User> updateUser(@PathVariable String user, @RequestBody User u) {

        User userUpdated = userService.updateUser(user, u);

        if (userUpdated != null) {

            return new ResponseEntity<>(userUpdated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/user/{user}")
    public ResponseEntity<User> deleteUser(@PathVariable String user) {
        User userToDelete = userService.removeUser(user);
        if (userToDelete != null) {
            return new ResponseEntity<>(userToDelete, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
