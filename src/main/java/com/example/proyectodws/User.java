package com.example.proyectodws;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class User {
    @Id
    private String user;
    private String lastName;

    @JsonIgnore //evita bucles infinitos
    private String password;



    @ManyToOne
    @JoinColumn(name="userGrade")
    private Grade grade;


    public User(String user, String password, String lastName) {
        this.user = user;
        this.password = password;
        this.lastName = lastName;
    }


    public String toString() {
        return "User [username=" + user + "]";
    }

    public boolean checkPass(String password) {
        return this.password.equals(password);
    }

    public Grade getGrade() {
        return grade;
    }

    /*  No es necesario
    public long getId() {
        return this.id;
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



    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }


     */



}



