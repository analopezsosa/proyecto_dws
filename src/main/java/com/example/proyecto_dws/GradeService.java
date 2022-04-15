package com.example.proyecto_dws;

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

    /* LO QUE HABIA EN EL HOLDER
    @Service
public class GradeHolder {
    private Map<Long,Grade> gradesM = new ConcurrentHashMap<>();
    private AtomicLong lastId = new AtomicLong();

    public Grade addGrade(Grade grade){
        long id = lastId.incrementAndGet();
        grade.setId(id);
        gradesM.put(id,grade);
        return grade;
    }
    public Grade updateGrade(long id, Grade grade){
        grade.setId(id);
        return gradesM.replace(id, grade);
    }
    public Grade deleteGrade(long id){
        return gradesM.remove(id);
    }
    public Collection<Grade> getGrades(){
        return gradesM.values();
    }
    public Grade getGrade(long id){
        return gradesM.get(id);
    }

     */



}
