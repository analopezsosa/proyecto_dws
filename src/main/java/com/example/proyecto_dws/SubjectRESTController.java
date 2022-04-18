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
    SubjectRepository repository;

    @Autowired
    SubjectService subjectService;

    @GetMapping("/subject")
    public ResponseEntity<Collection> subjectList() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/subject")
    public ResponseEntity<Subject> addSubject(@RequestBody Subject subject) {
        repository.save(subject);
        if (subject != null) {
            return new ResponseEntity<>(subject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }



    @PutMapping("/subject/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable long id,@RequestBody Subject subject){
        Subject aux = repository.getById(id);

        subjectService.updateSubject(aux);


        if(aux != null){
            return new ResponseEntity<>(aux, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/subject/{id}")
    public ResponseEntity<Subject> deleteSubject(@PathVariable long id){
        Subject aux = repository.getById(id);
         subjectService.removeSubject(id);
        if (aux != null){
            return new ResponseEntity<>(aux, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}

