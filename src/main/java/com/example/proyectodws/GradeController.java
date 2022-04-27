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
            gradeService.deleteGrade(id);
        }

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

    @GetMapping("/editgrade")
    public String edit(){
        return "editgrade";
    }
    @PostMapping("/editgrade")
    public String edited(@RequestParam long id, @RequestParam String name, @RequestParam int gradeNumber){
        Grade editThisGrade = gradeService.getGrade(id);

        editThisGrade.setName(name);
        editThisGrade.setGradeNumber(gradeNumber);
        gradeService.addGrade(editThisGrade);
        return "editedgrade";
    }
    /*@GetMapping("/grade/{id}/addusertograde")
    public String addUserToGrade(Model model, @PathVariable long id){
        userRepository.getById(id);

    }*/



}
