package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.owasp.html.Sanitizers;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Controller
public class GradeController {


    @Autowired
    private GradeService gradeService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @GetMapping("/viewgrades.html")
    public String showGrades(Model model){
        model.addAttribute("grades",gradeService.gradeList());
        return "viewgrades";
    }
    @GetMapping("/creategrade")
    public String showCreate(Model model) {
        loginDisplay(model);
        return "creategrade"; }

    @PostMapping("/creategrade")
    public String gradeCreated(Model model,@RequestParam String name,@RequestParam int gradeNumber,@RequestParam String teacher){
        loginDisplay(model);

        Grade grade = new Grade(name,gradeNumber,teacher);
        gradeService.saveGrade(grade);
        model.addAttribute("grade",grade);
        return "functionalities";
    }

    @GetMapping("/grade/{id}")
    public String viewGrade( Model model, @PathVariable long id) {;
        model.addAttribute("grade",gradeService.getGrade(id));
        return "viewsubjectsbygrade";
    }

    @GetMapping("/removegrade")
    public String showremove(){
        return "removegrade";
    }


    @PostMapping("/removegrade")
    public String deleteGrade(@RequestParam Long id){
        Grade grade = gradeService.getGrade(id);
        if(grade != null){
            removeUsers(id);
            gradeService.deleteGrade(id);
            return "functionalities";
        }
        return "error";

    }


    @GetMapping("/removeUsers")
    public String showRemoveU(){return "removeusers";}

    @PostMapping("/removeUsers")
    public String removeUsers(@RequestParam Long id){

        Grade grade=gradeService.getGrade(id);
        List<User> userToDeleteFromGrade=grade.getUserList();
        int x=userToDeleteFromGrade.size();
        for (int i=0;i<x;i++) {
            userToDeleteFromGrade.get(i).deleteGrade(grade);
        }
        grade.deleteUserList(userToDeleteFromGrade);
        gradeService.addGrade(grade);

        return "functionalities";
    }

    @GetMapping("/editgrade")
    public String edit(){
        return "editgrade";
    }
    @PostMapping("/editgrade")
    public String edited(@RequestParam long id, @RequestParam String name, @RequestParam int gradeNumber, @RequestParam String teacher){
        Grade editThisGrade = gradeService.getGrade(id);

        editThisGrade.setName(name);
        editThisGrade.setGradeNumber(gradeNumber);
        editThisGrade.setTeacher(teacher);
        gradeService.addGrade(editThisGrade);
        return "editedgrade";
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
