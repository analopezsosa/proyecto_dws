package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradeController {


    @Autowired
    private GradeRepository repository;




    @GetMapping("/viewgrades.html")
    public String showGrades(Model model){
        model.addAttribute("grades",repository.findAll());
        return "viewgrades";
    }
    @GetMapping("/creategrade")
    public String showCreate() { return "creategrade"; }


    @GetMapping("/creategrade/newgrade")
    public String createGrade(Model model, Grade grade){

        repository.save(grade);
        model.addAttribute("grade",grade);
        return "viewgrade";
    }
    @GetMapping("/grade/{id}")
    public String viewGrade( Model model, @PathVariable long id) {
        Grade grade = repository.findById(id).get();
        model.addAttribute("grade",grade);
        return "viewsubjectsbygrade";
    }


    @DeleteMapping("/grade/{id}")
    public Grade deleteGrade(@PathVariable Long id){
        Grade grade = repository.findById(id).get();
        repository.deleteById(id);
        return grade;
    }

    @PutMapping("/grade/{id}")
    public Grade updateGrade(Grade grade, Long id){
        Grade aux = repository.findById(id).get();
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
