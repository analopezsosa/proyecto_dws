package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.SecondaryTable;
import javax.persistence.TypedQuery;
import java.text.ParseException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.owasp.html.Sanitizers;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    GradeService gradeService;

    @Autowired
    QueryFilter QueryFilter;

    @Autowired
    private PasswordEncoder passwordEncoder;

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


        User newUser= new User(username,passwordEncoder.encode(password),lastName);
        userService.addUser(newUser);
        loginDisplay(model);
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
        }else if(passwordEncoder.matches(password, user.getPassword())){
            model.addAttribute("user", userService.getUser(username));
            if (user.getGrade()!= null) {
                model.addAttribute("userGrade", userService.getUser(username));
            }
            if (user.getRoles().contains("ADMIN")){
                return "functionalities"; //hay que hacer la pagina de admin
            }
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                model.addAttribute("admin", true);
            } else {
                model.addAttribute("username", auth.getName());
            }
            return "viewuser";  //crear una pagina unica para usuario
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
        User user = userService.getUser(username);

        if(user != null&&user.getGrade()!=null){
            Grade grade=user.getGrade();
            user.deleteGrade(grade);
            grade.deleteUser(user);
            gradeService.addGrade(grade);
            userService.removeUser(username);
        }else if(user!=null){
            userService.removeUser(username);

        }
        return "functionalities";

    }

    @PersistenceContext
    private EntityManager entityManager;
    @GetMapping("/filter")
    public String filterUsers(Model model, @RequestParam(required = false, name= "username") String username, @RequestParam(required = false, name = "lastName") String lastName){
        if (username!="" && lastName!=""){
            TypedQuery<User> q1 = entityManager.createQuery("SELECT u FROM User u where u.user = :username  and u.last_name= :lastName",User.class);
            model.addAttribute("users", q1.setParameter("username",username).getResultList());
        }
        else if (username!=""){
            TypedQuery<User> q2 = entityManager.createQuery("SELECT u FROM User u WHERE u.user = :username",User.class);
            model.addAttribute("users", q2.setParameter("username",username).getResultList());
        }
        else if (lastName!=""){
            model.addAttribute("viewusers", QueryFilter.userByLastName(username));
        }
        else{
            model.addAttribute("users",userService.getUsers());
        }

        return "viewusers";
    }
/*
    @GetMapping("/filter")
    public String filterUsers(Model model, @RequestParam(required = false, name= "username") String username, @RequestParam(required = false, name = "lastName") String lastName)  {

        if (username==""&&lastName==""){
            model.addAttribute("users",userService.getUsers());
        }
        else {
            Set<User> userSet = new HashSet<>(userService.getUsers());


            if (username != "" ) {
                userSet.retainAll(userService.userByUsername(username));
            }
            if (lastName!="") {
                userSet.retainAll(userService.userByLastname(lastName));
            }

            List<User> toPrint = new LinkedList<>(userSet);
            model.addAttribute("users", toPrint);
        }
        return "viewusers";
    }

 */

    @GetMapping("/viewuser/{user}")
    public String showUser(Model model, @PathVariable String user){
        if(!checkSession(user)) {
            model.addAttribute("403", user);
            return "error";
        }

        User u = userService.getUser(user);

        if (u != null) {
            model.addAttribute("user", u);
            if (u.getGrade() != null) {
                model.addAttribute("grade", u);
            }

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                model.addAttribute("admin", true);
            } else {
                model.addAttribute("username", auth.getName());
            }


        return "viewuser";
    }
    return "error";
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
    public String removeUserFromGrade(@RequestParam String name,@RequestParam long id){//convertirlo en que un usuario se salga de un curso

        User user=userService.getUser(name);
        Grade grade=gradeService.getGrade(id);
        user.deleteGrade(grade);
        grade.deleteUser(user);
        gradeService.addGrade(grade);


        return "functionalities";

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
    private boolean checkSession(String user){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return auth.getName() != null && (auth.getName().equals(user) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        }
        return false;
    }

}