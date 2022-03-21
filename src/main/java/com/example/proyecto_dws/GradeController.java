package com.example.proyecto_dws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GradeController {
    @Autowired
    GradeHolder gradeHolder;
    @Autowired
    SubjectHolder subjectHolder;
    @Autowired
    UserHolder userHolder;

    @GetMapping("/creategrade.html")
    public String showCreate() { return "creategrade"; }
    @PostMapping("/creategrade.html")
    public String createGrade(@RequestParam String name, @RequestParam int gradeNumber, Model model) {
        Grade newgrade = new Grade(name, gradeNumber);
        long idNewGrade = newgrade.getId();

        if(gradeHolder.getGrade(idNewGrade)== null ) { //hacer algo para que compruebe que no existe ya un grade que se llame igual
            gradeHolder.addGrade(new Grade(name, gradeNumber));
            model.addAttribute("grade", newgrade);
            return "viewgrades";
        }
        return "error";
    }



}
