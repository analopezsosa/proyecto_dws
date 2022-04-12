package com.example.proyecto_dws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserRepository repository;

    @GetMapping("/viewusers.html")
    public String showUsers(Model model){
        model.addAttribute("users",repository.findAll());
        return "viewgrades";
    }
    @PostMapping("/createusers.html")
    public String createUser(Model model, User user){
        repository.save(user);
        return "user_created";
    }
    @GetMapping("/user/{id}")
    public String showUser(Model model, @PathVariable long id) {
        User user = repository.findById(id).get();
        model.addAttribute("user", user);
        return "view_user";
    }


    /*
    @Autowired
    UserHolder userHolder;

    @GetMapping("/signup.html")
    public String showSignUp(){
        return "signup";
    }
    @PostMapping("/signup.html")
    public String signUpUser(@RequestParam String user, @RequestParam String password, Model model){
        User newuser = new User(user, password);

        if(userHolder.getUser(user) == null) {
            userHolder.addUser(user, newuser);
            model.addAttribute("user", newuser);
            return "login";
        }
        return "error";
    }


    @GetMapping("/login.html")
    public String showLogin() { return "login"; }
    @PostMapping("/login.html")
    public String logInUser(@RequestParam String user, @RequestParam String password, Model model){
        return "functionalities";

    }

          This code could be used to check if there is a user with that username and the correct password in the userHolder map

    @PostMapping("/login.html")
    public String logInUser(@RequestParam String user, @RequestParam String password, Model model){

       User userUsedInLogin =userHolder.getUser(user);
        if(userUsedInLogin==null || !userUsedInLogin.isPasswordCorrect(password)) {
            return "error";
        }
            return "functionalities";
        }

    }*/

}
