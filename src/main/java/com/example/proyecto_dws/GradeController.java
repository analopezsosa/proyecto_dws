package com.example.proyecto_dws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

        if(newgrade!=null) {
            gradeHolder.addGrade(newgrade);
            model.addAttribute("grades", newgrade);
            return "viewgrade";
        }
        return "error";
        /*
        if(gradeHolder.getGrade(newgrade.getId()) == null) {
            gradeHolder.addGrade(newgrade);
            model.addAttribute("grade", newgrade);
            return "viewgrades";
        }else{
            return "error";
        }*/

    }

    @GetMapping("/editgrade.html")
    public String showEdit() {
        return "editgrade";
    }
    @PostMapping("/editgrade.html")
    public String editGrade(@RequestParam long id, @RequestParam String name, @RequestParam int gradeNumber, Model model){
        Grade editThisGrade = gradeHolder.getGrade(id);
        if (editThisGrade!=null) {
            editThisGrade.setName(name);
            editThisGrade.setGradeNumber(gradeNumber);
            return "viewgrades";
        } return "error";
    }

    @GetMapping("/removegrade.html")
    public String showRemove() { return "removegrade"; }
    @PostMapping("/removegrade.html")
    public String removeGrade(@RequestParam long id, Model model){
        Grade gradeRemoved = gradeHolder.getGrade(id);

        if (gradeRemoved!=null){ //if there is a grade with the id that has been introduced
            List<String> subjectList = gradeRemoved.getSubjectsOfTheGrade();
            for (String subject : subjectList) {
                subjectHolder.getSubjects().remove(gradeRemoved);
            }
            return "viewgrades";
        }
        return "error";
    }

    @GetMapping("/viewgrades.html")
    public String showGrades(Model model){
        model.addAttribute("grades",gradeHolder.getGrades());
        return "viewgrades";
    }




}
