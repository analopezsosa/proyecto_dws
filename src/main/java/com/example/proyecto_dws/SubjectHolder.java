package com.example.proyecto_dws;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SubjectHolder {
    private Map<Long, Subject> subjectsM = new ConcurrentHashMap<>();
    private AtomicLong lastId = new AtomicLong();

    public void addSubject(Subject subject){
        long id  = lastId.incrementAndGet();
        subject.setId(id);
        subjectsM.put(id,subject);
    }
    public Subject updateSubject(long id, Subject subject){
        subject.setId(id);
        return subjectsM.replace(id, subject);
    }
    public Subject deleteSubject(long id){
        return subjectsM.remove(id);
    }
    public Collection<Subject> getSubjects(){
        return subjectsM.values();
    }
    public Subject getSubject(long id){
        return subjectsM.get(id);
    }

}
