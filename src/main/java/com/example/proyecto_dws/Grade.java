package com.example.proyecto_dws;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
    private String name;
    private int gradeNumber;
    private long id = -1;
    private List<Subject> subjectsOfTheGrade = new ArrayList<>();


    public Grade(String name, int gradeNumber){
        this.name = name;
        this.gradeNumber = gradeNumber;
    }

    public boolean addSubject(Subject subject) {
        if (!subjectsOfTheGrade.contains(subject)) {
            subjectsOfTheGrade.add(subject);
            return true;
        } else {
            return false;
        }
    }
    public void removeSubject(String id) {
        subjectsOfTheGrade.remove(id);
    }
    public List<Subject> getSubjectsOfTheGrade() {
        return subjectsOfTheGrade;
    }


    public String getName() {
        return name;
    }

    public int getGradeNumber(){
        return this.gradeNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return "Curso: "+name + " Número: "+ gradeNumber + ". Con ID: " + id;
    }

    public long getId(){
        return this.id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return gradeNumber == grade.gradeNumber && id == grade.id && Objects.equals(name, grade.name) && Objects.equals(subjectsOfTheGrade, grade.subjectsOfTheGrade);
    }
}
