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
    private PasswordEncoder passwordEncoder;


    @GetMapping("/signup")
    public String showSignUp(){
        return "signup";
    }
    @PostMapping("/signup")
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


    @GetMapping("/login")
    public String showLogin(){
        return "login";
    }
    @PostMapping("/login")
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
            //return "viewuser";  //crear una pagina unica para usuario
            return "functionalities";
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
            TypedQuery<User> q1 = entityManager.createQuery("SELECT u FROM User u where u.user = :username  and u.lastName= :lastName",User.class);
            q1.setParameter("lastName",lastName).setParameter("username",username);
            model.addAttribute("users", q1.getResultList());
        }
        else if (username!=""){
            TypedQuery<User> q2 = entityManager.createQuery("SELECT u FROM User u WHERE u.user = :username",User.class);
            model.addAttribute("users", q2.setParameter("username",username).getResultList());
        }
        else if (lastName!=""){
            TypedQuery<User> q2 = entityManager.createQuery("SELECT u FROM User u WHERE u.lastName = :lastName",User.class);
            model.addAttribute("users", q2.setParameter("lastName",lastName).getResultList());
        }
        else{
            model.addAttribute("users",userService.getUsers());
        }

        return "viewusers";
    }


    @GetMapping("/viewuser/{user}")
    public String showUser(Model model, @PathVariable String user){
        /*if(!checkSession(user)) {
            System.out.println("usa este metodo"); //para ver si siempre se pasa a este metodo
            model.addAttribute("403", user);
            return "error";
        }

         */

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
    @PostMapping("/joingradeU")
    public String joinGradeU(@RequestParam long id, Model model){
        loginDisplay(model);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userToJoin = userService.getUser(auth.getName());
        Grade gradeToJoin=gradeService.getGrade(id);
        if (userToJoin.getGrade()==null) {
            userToJoin.setGrade(gradeToJoin);
            gradeToJoin.addUser(userToJoin);
            userService.addUser(userToJoin);
            gradeService.addGrade(gradeToJoin);
            model.addAttribute("user", userService.getUser(auth.getName()));
            return "index";
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
        System.out.println("llega hasta aqui??");
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            System.out.println("funciona este metodo");
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