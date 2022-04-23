package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradeController {


    @Autowired
    private GradeService gradeService;




    @GetMapping("/viewgrades.html")
    public String showGrades(Model model){
        model.addAttribute("grades",gradeService.gradeList());
        return "viewgrades";
    }
    @GetMapping("/creategrade")
    public String showCreate() { return "creategrade"; }

    @PostMapping("/creategrade")
    public String gradeCreated(Model model, String name, int gradeNumber){
        Grade grade = new Grade(name,gradeNumber);
        gradeService.saveGrade(grade);
        model.addAttribute("grade",grade);
        return "functionalities";
    }

    @GetMapping("/grade/{id}")
    public String viewGrade( Model model, @PathVariable long id) {
        Grade grade = gradeService.getGrade(id);
        model.addAttribute("grade",grade);
        return "viewsubjectsbygrade";
    }


    @DeleteMapping("/grade/{id}")
    public String deleteGrade(@PathVariable Long id){
        Grade grade = gradeService.getGrade(id);
        gradeService.deleteGrade(id);
        return "functionalities";
    }

    @PutMapping("/grade/{id}")
    public Grade updateGrade(Grade grade, Long id){
        Grade aux = gradeService.getGrade(id);
        if(aux != null && !"".equalsIgnoreCase(grade.getName())){
            aux.setName(grade.getName());
            aux.setGradeNumber(grade.getGradeNumber());
        }
        return aux;
    }

    /*@GetMapping("/grade/{id}/addusertograde")
    public String addUserToGrade(Model model, @PathVariable long id){
        userRepository.getById(id);

    }*/



}
