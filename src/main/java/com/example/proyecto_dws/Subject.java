package com.example.proyecto_dws;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    private String name;
    private int subjectNumber;
    private long id =-1;

    public Subject(String name, int subjectNumber){
        this.name = name;
        this.subjectNumber = subjectNumber;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getSubjectNumber() {
        return subjectNumber;
    }
    public void setSubjectNumber(int subjectNumber) {
        this.subjectNumber = subjectNumber;
    }
    public String getName() {return name;}
    public void setName(String name) {
        this.name = name;
    }
    public String toString(){
        return "Asignatura: "+name + " Número: "+ subjectNumber + ". Con ID: " + id;
    }
}
