package com.example.proyecto_dws;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
    private String name;
    private int gradeNumber;
    private long id = -1;


    public Grade(String name, int gradeNumber){
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
        return "Curso: "+name + " Número: "+ gradeNumber + ". Con ID: " + id;
    }

}
