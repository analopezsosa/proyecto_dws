package com.example.proyecto_dws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
public class GradeController {
    /*
    @Autowired
    GradeHolder gradeHolder;
    @Autowired
    SubjectHolder subjectHolder;
    @Autowired
    UserHolder userHolder;

     */

    @Autowired
    private GradeRepository repository;

    @Autowired
    private UserRepository userRepository;
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
        model.addAttribute("grades",grade);
        //model.addAttribute("grades",);
        return "viewgrade";
    }
    @GetMapping("/grade/{id}")
    public String viewGrade( Model model, @PathVariable long id) {
        Grade grade = repository.findById(id).get();
        model.addAttribute("grade",grade);
        return "viewsubjectsbygrade";
    }
    /*@GetMapping("/grade/{id}/addusertograde")
    public String addUserToGrade(Model model, @PathVariable long id){
        userRepository.getById(id);

    }*/



/*
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

    }

   @GetMapping("/editgrade.html")
    public String showEdit() {
        return "editgrade";
    }
    @PostMapping("/editgrade.html")
    public String editGrade( Model model, @RequestParam long id, @RequestParam String name, @RequestParam int gradeNumber){
        Grade editThisGrade = gradeHolder.getGrade(id);
        if (editThisGrade!=null) {
            editThisGrade.setName(name);
            editThisGrade.setGradeNumber(gradeNumber);
            gradeHolder.updateGrade(id,editThisGrade);
            model.addAttribute("grade",editThisGrade);
            return "editedgrade";
        } return "error";
    }

    @GetMapping("/removegrade.html")
   public String showRemove() { return "removegrade"; }
    @PostMapping("/removegrade.html")
    public String removeGrade(@RequestParam long id, Model model){
        Grade gradeRemoved = gradeHolder.getGrade(id);

        if (gradeRemoved!=null){ //if there is a grade with the id that has been introduced
            List<Subject> subjectList = gradeRemoved.getSubjectsOfTheGrade();
            for (Subject subject : subjectList) {

                subjectHolder.deleteSubject(id,subject);
            }
            gradeHolder.deleteGrade(id);
            return "deletedgrade";
        }
        return "error";
    }


    @GetMapping("/functionalities.html")
    public String functionalities(Model model){
        model.addAttribute("grades",gradeHolder.getGrades());
        return "functionalities";
    }

    @GetMapping("/grade/{id}")
    public String getSubjects(@PathVariable long id, Model model){
        Collection<Subject> subjects = subjectHolder.getSubjectsByGrade(id);

        model.addAttribute("subjects",subjects);
        return "viewsubjectsbygrade";
    }

 */
}
