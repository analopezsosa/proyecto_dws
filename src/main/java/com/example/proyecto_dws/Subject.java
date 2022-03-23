package com.example.proyecto_dws;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    private String name;
    private int subjectNumber;
    private long subjectid =-1;
    private List<String> grades = new ArrayList<>();

    public Subject(String name, int subjectNumber){
        this.name = name;
        this.subjectNumber = subjectNumber;
    }

    public long getId() {
        return subjectid;
    }

    public void setId(long subjectid) {
        this.subjectid = subjectid;
    }

    public int getSubjectNumber() {
        return subjectNumber;
    }

    public void setSubjectNumber(int subjectNumber) {
        this.subjectNumber = subjectNumber;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return "Asignatura: "+name + " Número: "+ subjectNumber + ". Con ID: " + subjectid;
    }

    public void addGrade(String name){grades.add(name);}

    public void removeGrade(String name){grades.remove(name);}
}
