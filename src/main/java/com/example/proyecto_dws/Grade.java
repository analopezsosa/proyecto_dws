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
    private List<String> subjectsOfTheGrade = new ArrayList<>();


    public Grade(String name, int gradeNumber){
        this.name = name;
        this.gradeNumber = gradeNumber;
    }

    public boolean addSubject(String id) {
        if (!subjectsOfTheGrade.contains(id)) {
            subjectsOfTheGrade.add(id);
            return true;
        } else {
            return false;
        }
    }
    public void removeSubject(String id) {
        subjectsOfTheGrade.remove(id);
    }
    public List<String> getSubjects() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return gradeNumber == grade.gradeNumber && id == grade.id && Objects.equals(name, grade.name) && Objects.equals(subjectsOfTheGrade, grade.subjectsOfTheGrade);
    }
}
