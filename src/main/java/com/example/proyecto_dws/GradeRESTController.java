package com.example.proyecto_dws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/api")
@RestController
public class GradeRESTController {
    @Autowired
    GradeHolder gradeHolder;

    @GetMapping("/grades")
    public ResponseEntity<Collection> gradeList(){return new ResponseEntity<>(gradeHolder.getGrades(),HttpStatus.OK);}

    @GetMapping("/grades/{id}")
    public ResponseEntity<Grade> viewGrade(@PathVariable long id){
        return new ResponseEntity<>(gradeHolder.getGrade(id),HttpStatus.OK);}

    @PostMapping("/grades")
    public ResponseEntity<Grade> addGrade(@RequestBody Grade grade){
        Grade gradeT= gradeHolder.addGrade(grade);
        if(gradeT != null){
            return new ResponseEntity<>(gradeT, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @PutMapping("/grades/{id}")
    public ResponseEntity updateGrade(@PathVariable int id, @RequestBody Grade grade){
        Grade gradeT = gradeHolder.updateGrade(id,grade);

        if(gradeT != null){
            return new ResponseEntity<>(gradeT, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/grades/{id}")
    public ResponseEntity<Grade> deleteGrade(@PathVariable long id){
        Grade gradeT = gradeHolder.deleteGrade(id);
        if (gradeT != null){
            return new ResponseEntity<>(gradeT, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}




