package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/functionalities")
    public String joinGrade(@RequestParam String username,@RequestParam long id, Model model){
        User userJoined = userService.getUser(username);
        Grade gradeToJoin=gradeService.getGrade(id);
        //if (userJoined.getGrade()==null){

            userJoined.setGrade(gradeToJoin);
            gradeService.addGrade(gradeToJoin);
            userService.addUser(userJoined);
           // userJoined.getGrade().

        return "funcionalities";




        //}
/*
        Subject subjectToAdd = subjectService.getSubject(idS);
        Grade g = gradeService.getGrade(idG);

        g.addSubject(subjectToAdd);
        subjectService.addSubject(subjectToAdd);
        gradeService.addGrade(g);
        subjectToAdd.getGrades().add(g);

        model.addAttribute("grade",gradeService.getGrade(idG));
        return "viewsubjectsbygrade";
*/
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
