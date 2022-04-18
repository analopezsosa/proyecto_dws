package com.example.proyecto_dws;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private int subjectNumber;
    private String description;


    public Subject(String name, int subjectNumber, String description){
        this.name = name;
        this.subjectNumber = subjectNumber;
        this.description=description;
    }


    @ManyToMany //hay que poner mas cosas
    private List<Grade> grades = new ArrayList<>();


    public boolean addGrade(Grade grade){

        if (grades.contains(grade)){

            return false;
        }else{

            grades.add(grade);
            return true;
        }



    }

    public void deleteGrade(Grade grade){
        grades.remove(grade);
    }


}
