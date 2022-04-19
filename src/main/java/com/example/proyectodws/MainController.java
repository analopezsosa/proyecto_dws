package com.example.proyectodws;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login.html")
    public String showLogin() { return "login"; }

  //  @GetMapping("/signup.html")
    //public String showRegistrer() { return "signup"; }


}
