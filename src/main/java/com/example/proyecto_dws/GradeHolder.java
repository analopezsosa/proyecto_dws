package com.example.proyecto_dws;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

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


}
