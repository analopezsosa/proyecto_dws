package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {
    @Autowired
    GradeRepository gradeRepository;

    public void saveGrade(Grade grade){
        gradeRepository.save(grade);
    }

    public List<Grade> gradeList(){
        return gradeRepository.findAll();
    }

    public Grade getGrade(long id){
        Grade grade= gradeRepository.getById(id);
        return grade;
    }


    public Grade deleteGrade(long id){

       Grade grade= gradeRepository.getById(id);

       gradeRepository.deleteById(id);
       return grade;
    }



}
