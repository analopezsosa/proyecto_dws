package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private UserService userService;

    @GetMapping("/viewsubjects.html")
    public String showSubjects(Model model){
        loginDisplay(model);
        model.addAttribute("subjects",subjectService.getSubjectList());
        return "viewsubjects";
    }


    @GetMapping("/createsubject.html")
    public String showSubject(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            model.addAttribute("403", true);
            return "error";
        }
        loginDisplay(model);
        return "createsubject";}

    @PostMapping("/createsubject.html")
    public String createSubject(@RequestParam String name, @RequestParam int subjectNumber,@RequestParam String description, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("403", true);
            return "error";
        }
        loginDisplay(model);
        Subject newsubject = new Subject(name, subjectNumber,Sanitizers.FORMATTING.sanitize(description));
        subjectService.saveSubject(newsubject);
        model.addAttribute("subject",newsubject);

        return "viewsubject";
    }


    @GetMapping("/editsubject.html")
    public String showEdit(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            model.addAttribute("403", true);
            return "error";
        }
        loginDisplay(model);
        return "editsubject";
    }
    @PostMapping("/editsubject.html")
    public String editSubject(@RequestParam long id, @RequestParam String name, @RequestParam int subjectNumber, @RequestParam String description, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("403", true);
            return "error";
        }
        loginDisplay(model);
        Subject editThisSubject = subjectService.getSubject(id);

        editThisSubject.setName(name);
        editThisSubject.setSubjectNumber(subjectNumber);
        editThisSubject.setDescription(Sanitizers.FORMATTING.sanitize(description));
        subjectService.addSubject(editThisSubject);
        return "editedsubject";
    }

    @GetMapping("/addsubjecttograde")
    public String showAddSubject(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            model.addAttribute("403", true);
            return "error";
        }
        loginDisplay(model);
        return "addsubjecttograde";
    }


    @PostMapping("/addsubjecttograde")
    public String addingSubjectToGrade(@RequestParam long idS, @RequestParam long idG, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("403", true);
            return "error";
        }
        loginDisplay(model);
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
    public String showRemove(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            model.addAttribute("403", true);
            return "error";
        }
        loginDisplay(model);
        return "removesubject";
    }

    @PostMapping("/removesubject.html")
    public String removeSubject( @RequestParam Long id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("403", true);
            return "error";
        }
        loginDisplay(model);
        Subject subject = subjectService.getSubject(id);
        if(subject != null){

            deleteGrades(id);
            subjectService.deleteSubject(id);

            return "functionalities";
        }
        return "error";



    }

    @GetMapping("/removesubjectfromgrade")
    public String showRemoveFromGrade(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            model.addAttribute("403", true);
            return "error";
        }
        loginDisplay(model);
        return "removesubjectfromgrade";
    }
    @PostMapping("/removesubjectfromgrade")
    public String remove(@RequestParam Long idS, @RequestParam Long idG, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("403", true);
            return "error";
        }
        loginDisplay(model);
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



    private void loginDisplay(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            model.addAttribute("isLogged", true);
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                model.addAttribute("admin", true);
            } else {
                model.addAttribute("username", userService.getUser(auth.getName()));
            }
        }
    }

    private boolean checkSession(String user){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return auth.getName() != null && (auth.getName().equals(user) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        }
        return false;
    }















}
