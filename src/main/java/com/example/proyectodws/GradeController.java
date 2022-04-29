package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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



    @GetMapping("/viewgrades.html")
    public String showGrades(Model model){
        model.addAttribute("grades",gradeService.gradeList());
        return "viewgrades";
    }
    @GetMapping("/creategrade")
    public String showCreate() { return "creategrade"; }

    @PostMapping("/creategrade")
    public String gradeCreated(Model model, String name, int gradeNumber, String teacher){
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


}
