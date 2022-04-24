package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {
    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    SubjectRepository subjectRepository;

    public void saveGrade(Grade grade){
        gradeRepository.save(grade);
    }

    public List<Grade> gradeList(){
        return gradeRepository.findAll();
    }

    public Grade getGrade(long id){
        return gradeRepository.getById(id);
    }


    public Grade deleteGrade(long id){

       Grade grade= gradeRepository.getById(id);

       gradeRepository.deleteById(id);
       return grade;
    }

    public void addSubjectToGrade(long idS, long idG){
        Grade g = gradeRepository.getById(idG); //tengo el grado
        g.addSubject(subjectRepository.getById(idS)); //añado la asig al grado
    }
}
