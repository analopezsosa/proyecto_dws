package com.example.proyecto_dws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("users",userRepository.findAll());
        return "viewusers";
    }

    @GetMapping("/user/{id}")
    public String showUser(Model model, @PathVariable long id) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "viewuser";
    }
    @GetMapping("removeUser")
    public String removeUser(){
            return "removeUser";
    }
    @PostMapping("removeUser")
    public String deleteUser(@PathVariable Long id){
        User aux = userRepository.getById(id);
        userRepository.delete(aux);
        return "viewusers";
    }
}
