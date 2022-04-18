package com.example.proyecto_dws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RequestMapping("/api")
@RestController
public class GradeRESTController {
    @Autowired
    private GradeService gradeService;

    @Autowired

    private GradeRepository repository;

    @GetMapping("/grades")
    public ResponseEntity<Collection> gradeList(){return new ResponseEntity<>(repository.findAll(),HttpStatus.OK);}

    @GetMapping("/grades/{id}")
    public ResponseEntity<Grade> viewGrade(@PathVariable long id){
        Optional<Grade> op = repository.findById(id);
        if(op.isPresent()){
            Grade grade = op.get();
            return new ResponseEntity<>(grade,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/grades")
    public ResponseEntity<Grade> addGrade(@RequestBody Grade grade){
        repository.save(grade);
        if(grade != null){
            return new ResponseEntity<>(grade, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @PutMapping("/grades/{id}")
    public ResponseEntity updateGrade(@PathVariable long id, @RequestBody Grade grade){
        Grade gradeT = gradeService.getGrade(id);

        if(gradeT != null){
            gradeService.saveGrade(gradeT);
            return new ResponseEntity<>(gradeT, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/grades/{id}")
    public ResponseEntity<Grade> deleteGrade(@PathVariable long id){
        Grade gradeT = gradeService.deleteGrade(id);
        if (gradeT != null){
            return new ResponseEntity<>(gradeT, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}




