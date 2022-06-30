package com.example.proyectodws;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    UserService userService;

  /*  @GetMapping("/")
    public String mainPage(Model model){
        loginDisplay(model);
        return "index";
    }*/

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }




    private void loginDisplay(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            model.addAttribute("isLogged", true);
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                model.addAttribute("admin", true);
            } else {
                model.addAttribute("username", userService.getUser(auth.getName()));
            }
        }
    }


}
