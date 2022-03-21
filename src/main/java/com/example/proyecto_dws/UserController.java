package com.example.proyecto_dws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    UserHolder userHolder;

    @GetMapping("/signup.html")
    public String showSignUp(){
        return "signup";
    }
    @PostMapping("/signup.html")
    public String signUpUser(@RequestParam String user, @RequestParam String password, Model model){
        User newuser = new User(user, password);

        if(newuser.getUser(user) != null) {
            userHolder.addUser(new User(user, password));
            model.addAttribute("user", newuser);
            return "login";
        }
        return "error";
    }

    @GetMapping("/login.html")
    public String showLogin() { return "login"; }
    @PostMapping("/login.html")
    public String logInUser(@RequestParam String user, @RequestParam String password, Model model){
        User userUsedInLogin =userHolder.getUser(user);

        if(userUsedInLogin.isPasswordCorrect(password)){
            model.addAttribute("user", userHolder.getUser(user));
            return "viewgrades";
        }else{
            return "error";
        }

    }
}
