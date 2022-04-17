package com.example.proyecto_dws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    GradeRepository gradeRepository;

    //AQUI AÑADO TODA LA MIERDA QUE HABIA EN EL HOLDER
    /*

    @Service
public class SubjectHolder {
    private Map<Long, Subject> subjectsM = new ConcurrentHashMap<>();
    private AtomicLong lastId = new AtomicLong();

    private Map<Long, List<Subject>> subjectsByGradeId = new ConcurrentHashMap<>();

    public void addSubject(long id, Subject subject){
        List<Subject> aux;
        if(subjectsByGradeId.get(id) == null){
            aux = new java.util.ArrayList<>(Collections.emptyList());

        }else{
            aux = subjectsByGradeId.get(id);

        }
        aux.add(subject);
        subjectsByGradeId.put(id,aux);
    }
    public void deleteSubject(long id, Subject subject){
        subjectsByGradeId.get(id).remove(subject);
    }

    public Collection<Subject> getSubject(long id){
        return subjectsByGradeId.get(id);
    }

    public Subject getSubjectS(long id){ return subjectsM.get(id);}

    public Collection<Subject> getSubjectsByGrade(long id){
        return subjectsByGradeId.get(id);
    }

    public Collection<Subject> getSubjects(){
        return subjectsM.values();
    }

    public Subject updateSubject(long id, Subject subject){
        subject.setId(id);
        return subjectsM.replace(id, subject);
    }


    public void addNewSubject(Subject subject){
        long id  = lastId.incrementAndGet();
        subject.setId(id);
        subjectsM.put(id,subject);
    }

    public Subject deleteSubject(long id){
        return subjectsM.remove(id);
    }




     */




}