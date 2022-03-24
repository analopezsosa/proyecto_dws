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
    public String addingSubjectToGrade(@RequestParam long idS, @RequestParam long idG, Model model){
        Subject subjectToAdd = subjectHolder.getSubjectS(idS);
        Grade gradeToAdd=gradeHolder.getGrade(idG);

        //check if the subject and the grade exist
        if (gradeToAdd == null) {
            model.addAttribute("error", true);
            return "error";
        } else if (subjectToAdd == null) {
            model.addAttribute("error", true);
            return "error";

        }
        //check if there is already that subject in the list "subjectsOfTheGrade" of the grade
        else if (!gradeToAdd.addSubject(subjectToAdd)) {
            model.addAttribute("error",true);
            return "error";
        }
        else {
            gradeHolder.getGrade(idG).addSubject(subjectToAdd);
            return "addedsubjecttograde.html";
        }

    }

/*
   // @GetMapping ("/addsubjecttograde.html")
    //public String showAddSubject(){return "addsubjecttograde";}
    @GetMapping("/addsubjecttograde.html")
    public String addSubjectToGrade(@RequestParam String name, @RequestParam Long id, Model model){
        Grade editedGrade=gradeHolder.getGrade(id);
        Subject newSubject=subjectHolder.getSubject(id);
        if (newSubject==null) {
            model.addAttribute("error",true);
            return "signup";
        } else if(editedGrade==null) {
            model.addAttribute("error",true);
            return "creategrade";
        } else if(!editedGrade.addSubject(name)) {
            model.addAttribute("error",true);
            return "editsubject";
        } else {
            model.addAttribute("grade",gradeHolder.getGrade(id));
            gradeHolder.getGrade(id).addSubject(name);



            return "editsubject";
        }







       /*
        Subject newsubject=subjectHolder.getSubject(id);

        if (newsubject!=null){
            //if (!gradeHolder.containsSubject(newsubject)) {//here you can compare if we have added the subject previously
            subjectHolder.getSubject(id).addGrade(name); //espero que funcione asi porque sería modo "basico"

            return "createsubject";
            }else{
                return "error";
            }




    }*/

    @GetMapping("/removesubject.html")
    public String showRemove() { return "removesubject"; }
    @PostMapping("/removesubject.html")
    public String removeSubject( @RequestParam Long id){

       // Subject removedSubject= subjectHolder.getSubject(id);
        //if (removedSubject!=null){

            subjectHolder.deleteSubject(id);//it is suposed to delete the subject but im not sure if it works too inside the grade, we have to try
            return "deletedsubject";
       // }


    }


    // @GetMapping("/{id}")
    // public String showSubject(@PathVariable String name){} Im not sure if we need this, it is a method to see the info of a subject





}
