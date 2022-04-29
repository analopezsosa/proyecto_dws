package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import org.owasp.html.Sanitizers;


@Controller
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private GradeService gradeService;

    @GetMapping("/viewsubjects.html")
    public String showSubjects(Model model){
        model.addAttribute("subjects",subjectService.getSubjectList());
        return "viewsubjects";
    }



    @GetMapping("/createsubject.html")
    public String showSubject(){return "createsubject";}

    @PostMapping("/createsubject.html")
    public String createSubject(@RequestParam String name, @RequestParam int subjectNumber,@RequestParam String description, Model model){
        Subject newsubject = new Subject(name, subjectNumber,Sanitizers.FORMATTING.sanitize(description));
        subjectService.saveSubject(newsubject);
        model.addAttribute("subject",newsubject);

        return "viewsubject";
    }


    @GetMapping("/editsubject.html")
    public String showEdit() {
        return "editsubject";
    }
    @PostMapping("/editsubject.html")
    public String editSubject(@RequestParam long id, @RequestParam String name, @RequestParam int subjectNumber, @RequestParam String description){

        Subject editThisSubject = subjectService.getSubject(id);

        editThisSubject.setName(name);
        editThisSubject.setSubjectNumber(subjectNumber);
        editThisSubject.setDescription(Sanitizers.FORMATTING.sanitize(description));
        subjectService.addSubject(editThisSubject);
        return "editedsubject";
    }

    @GetMapping("/addsubjecttograde")
    public String showAddSubject(){
        return "addsubjecttograde";
    }


    @PostMapping("/addsubjecttograde")
    public String addingSubjectToGrade(@RequestParam long idS, @RequestParam long idG, Model model){
        Subject subjectToAdd = subjectService.getSubject(idS);
        Grade g = gradeService.getGrade(idG);

        g.addSubject(subjectToAdd);
        subjectService.addSubject(subjectToAdd);
        gradeService.addGrade(g);
        subjectToAdd.getGrades().add(g);

        model.addAttribute("grade",gradeService.getGrade(idG));
        return "viewsubjectsbygrade";
    }


    @GetMapping("/removesubject.html")
    public String showRemove() { return "removesubject"; }
    @PostMapping("/removesubject.html")
    public String removeSubject( @RequestParam Long id){
        Subject subject = subjectService.getSubject(id);
        if(subject != null){

            deleteGrades(id);
            subjectService.deleteSubject(id);

            return "functionalities";
        }
        return "error";



    }

    @GetMapping("/removesubjectfromgrade")
    public String showRemoveFromGrade(){
        return "removesubjectfromgrade";
    }
    @PostMapping("/removesubjectfromgrade")
    public String remove(@RequestParam Long idS, @RequestParam Long idG, Model model){
        Subject subjectToRemove = subjectService.getSubject(idS);
        Grade g = gradeService.getGrade(idG);

        g.deleteSubject(subjectToRemove);
        subjectToRemove.getGrades().remove(g);

        gradeService.addGrade(g);
        model.addAttribute("grade",gradeService.getGrade(idG));
        return "viewsubjectsbygrade";
    }



    public void deleteGrades(long id){
        Subject subject = subjectService.getSubject(id);
        List<Grade> gradesToDeleteFromSubject=subject.getGrades();
        int x=gradesToDeleteFromSubject.size();
        for (int i=0;i<x;i++) {
            gradesToDeleteFromSubject.get(i).deleteSubject(subject);
        }
        subject.deleteGradeList(gradesToDeleteFromSubject);
        subjectService.addSubject(subject);

    }




















}
