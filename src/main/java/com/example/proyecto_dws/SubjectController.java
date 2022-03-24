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
    public String showSubjects(Model model){
        model.addAttribute("subject",subjectHolder.getSubjects());
        return "viewsubjects";
    }

    @GetMapping("/createsubject.html")//esto creo que asi no es pero si lo pongo "bien" no ejecuta el codigo
    public String showSubject(){return "createsubject";}

    @PostMapping("/createsubject.html")
    public String createSubject(@RequestParam String name, @RequestParam int subjectNumber, Model model){
        Subject newsubject=new Subject(name,subjectNumber);

        if(newsubject!=null) {
            subjectHolder.addSubject(newsubject);
            model.addAttribute("subject", newsubject);
            return "viewsubject";
        }
        return "error";
    }

/*Otra opcion de createsubject si no funciona es hacerlo pidiendo el nombre del grade al que va a entrar

    @PostMapping("/createsubject.html")
    public String createSubject(@RequestParam String gradename, @RequestParam String name, @RequestParam int subjectNumber){
        Subject newsubject=new Subject(name,subjectNumber);

        newsubject.addGrade(gradename);
        gradeHolder.getGrade(gradename).addSubject(newsubject);
        subjectHolder.addSubject(newsubject);

        return "viewsubject";
    }



 */

    @GetMapping("/editsubject.html")
    public String showEdit() {
        return "editsubject";
    }
    @PostMapping("/editsubject.html")
    public String editGrade(@RequestParam long id, @RequestParam String name, @RequestParam int gradeNumber){
        Subject editThisSubject = subjectHolder.getSubject(id);
        if (editThisSubject!=null) {
            editThisSubject.setName(name);
            editThisSubject.setSubjectNumber(gradeNumber);
            return "viewsubjects";
        } return "error";
    }


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


*/

    }

    @GetMapping("/removesubject.html")
    public String showRemove() { return "removesubject"; }
    @PostMapping("/removesubject.html")
    public String removeSubject( @RequestParam Long id){

        Subject removedSubject= subjectHolder.getSubject(id);
        if (removedSubject!=null){

            subjectHolder.deleteSubject(id);//it is suposed to delete the subject but im not sure if it works too inside the grade, we have to try
            return "removesubject";
        }

        return "error";
    }


    // @GetMapping("/{id}")
    // public String showSubject(@PathVariable String name){} Im not sure if we need this, it is a method to see the info of a subject





}
