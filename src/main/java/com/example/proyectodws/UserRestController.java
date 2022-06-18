package com.example.proyectodws;


import com.fasterxml.jackson.annotation.JsonView;
import org.owasp.html.Sanitizers;
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
    @JsonView(View.Base.class)
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
    @GetMapping("user/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username){
        User user = userService.getUser(username);
        if(user != null){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/user/{user}")
    public ResponseEntity<User> updateUser(@PathVariable String user, @RequestBody User userToEdit) {

       User user1=userService.getUser(user);

        if (user1 != null) {
            userService.removeUser(user);
            userService.addUser(userToEdit);


            return new ResponseEntity<>(userToEdit, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




    @DeleteMapping("/user/{user}")
    public ResponseEntity<User> deleteUser(@PathVariable String user) {
        User userToDelete = userService.removeUser(user);
        if (userToDelete != null) {
            return new ResponseEntity<>( HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
