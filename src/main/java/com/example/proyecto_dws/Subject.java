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

    @ManyToMany
    private List<Grade> grades;

    public Subject(String name, int subjectNumber){
        this.name = name;
        this.subjectNumber = subjectNumber;
    }

}
