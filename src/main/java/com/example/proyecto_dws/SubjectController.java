package com.example.proyecto_dws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/subjects")
public class SubjectController {
    @Autowired
    GradeHolder gradeHolder;
    @Autowired
    SubjectHolder subjectHolder;


    @GetMapping("/viewsubject.html")
    public String showSubject(){return "viewsubjects";}

    @PostMapping("/createsubject.html")
    public String createSubject(@RequestParam String name, @RequestParam int subjectNumber){
        Subject newsubject=new Subject(name,subjectNumber);

        newsubject.addGrade(name);
        subjectHolder.addSubject(newsubject);

        return "viewsubjects"; //maybe we need a page to say "your subject is created"
    }


    @GetMapping ("/showsubject.html")
    public String showAddSubject(){return "createsubject";}//AQUI HAY QUE REDIGIRIR A UNA PAGINA QUE TE DIGA QUE TE HAS UNIDO CORRECTAMENTE

    @GetMapping("/createsubject.html")
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


    @PostMapping("/removesubject.html")
    public String deleteSubject( @RequestParam Long id){

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
