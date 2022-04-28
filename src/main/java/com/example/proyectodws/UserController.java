package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.SecondaryTable;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    GradeService gradeService;

    @GetMapping("/signup.html")
    public String showSignUp(){
        return "signup";
    }
    @PostMapping("/signup.html")
    public String registerUser(@RequestParam String username,@RequestParam String password,@RequestParam String lastName, Model model){


        if(userService.getUser(username)!=null) {
            model.addAttribute("error",true);
            return "signup";
        }


        User newUser= new User(username,password,lastName);
        userService.addUser(newUser);
        return "login";
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

    @GetMapping("/viewusers")
    public String showAllUsers(Model model){
        model.addAttribute("users",userService.getUsers());
        return "viewusers";
    }

    @GetMapping("/removeUser")
    public String showremove(){
        return "removeUser";
    }
    @PostMapping("/removeUser")
    public String removeUser(@RequestParam String username){
        User u = userService.getUser(username);
        if(u != null){
            userService.removeUser(username);
        }
        return "functionalities";

    }

    @GetMapping("/functionalities")
    public String showFunctionalities(){
        return "functionalities";
    }
    @PostMapping("/joingrade")
    public String joinGrade(@RequestParam String username,@RequestParam long id, Model model){


        User userToJoin = userService.getUser(username);
        Grade gradeToJoin=gradeService.getGrade(id);

        if (userToJoin.getGrade()==null) {

            userToJoin.setGrade(gradeToJoin);
            gradeToJoin.addUser(userToJoin);
            userService.addUser(userToJoin);
            gradeService.addGrade(gradeToJoin);


            model.addAttribute("user", userService.getUser(username));

            return "functionalities";

        }else{
            return "error";
        }
    }

    @GetMapping("/removeuserfromgrade")
    public String showremovefrom(){
        return "removeuserfromgrade";
    }
    @PostMapping("/removeuserfromgrade")
    public String removefromgrade(@RequestParam String name,@RequestParam long id){

        User usertoremove = userService.getUser(name);
        Grade g = gradeService.getGrade(id);
        g.deleteUser(usertoremove);
        gradeService.addGrade(g);
        usertoremove.deleteGrade(g);
        userService.addUser(usertoremove);

        return "functionalities";

    }


    @GetMapping("/filter")
    public String filterUsers(@RequestParam(required = false, name= "username") String username, @RequestParam(required = false, name = "lastName") String lastName, Model model) throws ParseException {
        Set<User> userSet = new HashSet<>(userService.getUsers());

        if (username != null) {
            userSet.retainAll(userService.userByUsername(username));
        }
        if (lastName != null) {
            userSet.retainAll(userService.userByLastname(lastName));
        }

        return "viewusers";
    }



}
