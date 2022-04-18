package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    GradeRepository gradeRepository;

    public boolean newSubject(Subject subject) {
        if(!subjectRepository.existsById(subject.getId())) {
            subjectRepository.save(subject);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateSubject(Subject subject) {
        if (subjectRepository.existsById(subject.getId())) {
            subjectRepository.save(subject);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeSubject(Long id){
        subjectRepository.deleteById(id);
        return true;
    }


}
