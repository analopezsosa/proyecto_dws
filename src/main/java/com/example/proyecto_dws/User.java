package com.example.proyecto_dws;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String user;
    private String password;


    public User(String user, String password){
        this.user=user;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser(String user) {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "User [id=" + id + ", username=" + user+"]";
    }

/*
    public boolean isPasswordCorrect (String passwordEntered){
        return this.password.equals(passwordEntered);
    }



    public boolean addGrade (String grade) {
        if (!gradesOfTheUser.contains(grade)) {
            gradesOfTheUser.add(grade);
            return true;
        }
        return false;
    }
    public void removeGrade(String grade) {
        gradesOfTheUser.remove(grade);
    }
    public List<String> getGrades() {
        return gradesOfTheUser;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user1 = (User) o;
        return Objects.equals(user, user1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }*/

}
