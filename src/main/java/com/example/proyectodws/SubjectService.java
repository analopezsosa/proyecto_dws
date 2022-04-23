package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;

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
    public List<Subject> getSubjectList(){
        return subjectRepository.findAll();
    }
    public void saveSubject(Subject s){
        subjectRepository.save(s);
    }

    public Subject getSubject(long id){

        return subjectRepository.getById(id);
    }

    public Subject deleteSubject(long id){

        Subject s= subjectRepository.getById(id);

        subjectRepository.deleteById(id);
        return s;
    }

}
