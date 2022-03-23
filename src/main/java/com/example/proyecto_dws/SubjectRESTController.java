package com.example.proyecto_dws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/api")
@RestController
public class SubjectRESTController {
    @Autowired
    SubjectHolder subjectHolder;

    @GetMapping("/subject")
    public ResponseEntity<Collection> subjectList(){return new ResponseEntity<>(subjectHolder.getSubjects(), HttpStatus.OK);}

    @PostMapping("/subject")
    public ResponseEntity<Subject> addSubject(@RequestBody Subject subject){
        subjectHolder.addSubject(subject);
        if(subject != null){
            return new ResponseEntity<>(subject, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }


    @PutMapping("/subject/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable long id,@RequestBody Subject subject){
        Subject aux = subjectHolder.updateSubject(id,subject);

        if(aux != null){
            return new ResponseEntity<>(aux, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/subject/{id}")
    public ResponseEntity<Subject> deleteSubject(@PathVariable long id){
        Subject aux = subjectHolder.deleteSubject(id);
        if (aux != null){
            return new ResponseEntity<>(aux, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}

