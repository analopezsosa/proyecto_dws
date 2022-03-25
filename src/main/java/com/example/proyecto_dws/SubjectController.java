package com.example.proyecto_dws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class SubjectController {
    @Autowired
    GradeHolder gradeHolder;
    @Autowired
    SubjectHolder subjectHolder;


    @GetMapping("/viewsubjects.html")
    public String showGrades(Model model){
        model.addAttribute("subjects",subjectHolder.getSubjects());
        return "viewsubjects";
    }


    @GetMapping("/createsubject.html")
    public String showSubject(){return "createsubject";}

    @PostMapping("/createsubject.html")
    public String createSubject(@RequestParam String name, @RequestParam int subjectNumber, Model model){
        Subject newsubject = new Subject(name, subjectNumber);

        if(newsubject!=null) {
            subjectHolder.addNewSubject(newsubject);
            model.addAttribute("subjects", newsubject);
            return "viewsubject";
        }
        return "error";
    }


    @GetMapping("/editsubject.html")
    public String showEdit() {
        return "editsubject";
    }
    @PostMapping("/editsubject.html")
    public String editSubject(@RequestParam long id, @RequestParam String name, @RequestParam int subjectNumber){
        Subject editThisSubject = subjectHolder.getSubjectS(id);
        if (editThisSubject!=null) {
            editThisSubject.setName(name);
            editThisSubject.setSubjectNumber(subjectNumber);
            return "editedsubject";
        } return "error";
    }


    @GetMapping("/addsubjecttograde.html")
    public String showAddSubject(){
        return "addsubjecttograde";
    }
    @PostMapping("/addsubjecttograde.html")
    public String addingSubjectToGrade(@RequestParam long idS, @RequestParam long idG){
        Subject subjectToAdd = subjectHolder.getSubjectS(idS);
        subjectHolder.addSubject(idG,subjectToAdd);
        gradeHolder.getGrade(idG).addSubject(subjectToAdd);
        return "addedsubjecttograde";
    }

    @GetMapping("/removesubjectfromgrade.html")
    public String showRemovSubjectFromGrade(){
        return "removesubjectfromgrade";
    }
    @PostMapping("/removesubjectfromgrade.html")
    public String removingSubjectFromGrade(@RequestParam long idS,@RequestParam long idG){
        Subject subjectToRemove = subjectHolder.getSubjectS(idS);
        subjectHolder.deleteSubject(idG,subjectToRemove);
        gradeHolder.getGrade(idG).removeSubject(subjectToRemove);
        return "removedsubjectfromgrade";
    }

    @GetMapping("/removesubject.html")
    public String showRemove() { return "removesubject"; }
    @PostMapping("/removesubject.html")
    public String removeSubject( @RequestParam Long id){

        subjectHolder.deleteSubject(id);//it is suposed to delete the subject but im not sure if it works too inside the grade, we have to try
        return "deletedsubject";



    }





}
