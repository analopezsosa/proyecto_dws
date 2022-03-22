package com.example.proyecto_dws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/grades")
public class SubjectController {
    @Autowired
    GradeHolder gradeHolder;
    @Autowired
    SubjectHolder subjectHolder;


    @GetMapping("/createsubject")
    public String showSubject(){return "createsubject";}

    @PostMapping("/createsubject")
    public String createSubject(@RequestParam String name, @RequestParam int subjectNumber){
        Subject newsubject=new Subject(name,subjectNumber);

        newsubject.addGrade(name);
        subjectHolder.addSubject(newsubject);

        return "index";
    }


    @GetMapping ("/addSubjectToGrade")
    public String showAddSubject(){return "addSubjectToGrade";}//AQUI HAY QUE REDIGIRIR A UNA PAGINA QUE TE DIGA QUE TE HAS UNIDO CORRECTAMENTE

    @GetMapping("/addSubjectToGrade")
    public String addSubjectToGrade(@RequestParam String name, @RequestParam String gradename){
        Subject newsubject=subjectHolder.getSubjects();


    }


}
