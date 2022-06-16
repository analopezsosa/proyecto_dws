package com.example.proyectodws;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/api")
@RestController
public class SubjectRESTController {
    @Autowired
    SubjectRepository repository;

    @Autowired
    SubjectService subjectService;

    @GetMapping("/subjects")
    public ResponseEntity<Collection> subjectList() {
        return new ResponseEntity<>(subjectService.subjectList(), HttpStatus.OK);
    }


    @GetMapping("/subjects/{id}")
    public ResponseEntity<Subject> viewSubject(@PathVariable long id){
        Subject subject=subjectService.getSubject(id);
        if(subject!=null){
            return new ResponseEntity<>(subject,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/subjects")
    public ResponseEntity<Subject> addSubject(@RequestBody Subject subject) {
        subjectService.addSubject(subject);
        if (subject != null) {
            return new ResponseEntity<>(subject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/subjects/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable long id, @RequestBody Subject subject){

        Subject editThisSubject = subjectService.updateSubject(id,subject);


        if(editThisSubject != null){
            return new ResponseEntity<>(editThisSubject, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<Subject> deleteSubject(@PathVariable long id){
        Subject subjectT = subjectService.deleteSubject(id);
        if (subjectT != null){
            return new ResponseEntity<>( HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}

