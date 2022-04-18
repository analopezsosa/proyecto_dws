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
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private int gradeNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<User> userList = new ArrayList<>();

    @ManyToMany(mappedBy = "grades")
    private List<Subject> subjects;




    public Grade(String name, int gradeNumber) {
        this.name = name;
        this.gradeNumber = gradeNumber;
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
        return " Curso: "+name + " Número: "+ gradeNumber + ". Con ID: " + id;
    }

    public long getId(){
        return this.id;
    }

    public List<User> getUserList(){
        return this.userList;
    }
    public void setUsers(List<User> users){
        this.userList = users;
    }

    public void setGradeNumber(int gradeNumber) {
        this.gradeNumber = gradeNumber;
    }
}

