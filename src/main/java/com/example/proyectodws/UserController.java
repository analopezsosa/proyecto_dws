package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/signup.html")
    public String showSignUp(){
        return "signup";
    }
    @PostMapping("/signup.html")
    public String registerUser(@RequestParam String username,@RequestParam String password,Model model){


        if(userService.getUser(username)!=null) {
            model.addAttribute("error",true);
            return "signup";
        }


        User newUser= new User(username,password);
        userService.addUser(newUser);
        return "index";
    }

    @GetMapping("/login.html")
    public String showLogin(){
        return "login";
    }
    @PostMapping("/login.html")
    public String loginUser(@RequestParam String username,@RequestParam String password, Model model){
        User user=userService.getUser(username);
        if(user==null) {
            model.addAttribute("notRegistered",true);
            return "signup";
        }else if(user.checkPass(password)){
            model.addAttribute("user", userService.getUser(username));
            if (user.getGrade()!= null) {
                model.addAttribute("userGrade", userService.getUser(username));
            }
            return "functionalities"; //pagina de gestion de usuario donde le sale para por ejemplo unirse a un grado, etc
        } else {
            model.addAttribute("error",true);
            return "login";
        }
    }
    @GetMapping("/functionalities")
    public String showFunctionalities(){
        return "functionalities";
    }
    /*

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
    @GetMapping("/removeUser")
    public String removeUser(){
            return "removeUser";
    }
    @PostMapping("/removeUser")
    public String deleteUser(@PathVariable Long id){
        User aux = userRepository.getById(id);
        userRepository.delete(aux);
        return "viewusers";
    }


    @GetMapping("/signup.html")
    public String showRegistrer() { return "signup"; }

    @GetMapping("/signup/new")
    public String addUser(Model model,@RequestParam String user,@RequestParam String password ){//add a user


            User user1=new User(user,password);
            userRepository.save(user1);
            model.addAttribute("user",user1);

            return "login";



    }

     */
}
