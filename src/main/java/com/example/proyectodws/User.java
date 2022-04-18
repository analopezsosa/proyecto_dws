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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String user;

    @JsonIgnore //evita bucles infinitos
    private String password;



    @ManyToOne
    @JoinColumn(name="userGrade")
    private Grade grade;


    public User(String user, String password,Grade grade) {
        this.user = user;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.grade = grade;
    }


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

    public String toString() {
        return "User [id=" + id + ", username=" + user + "]";
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    //Otra opcion no puede ser que sea asi??
    //@OneToMany(mappedBy = "user")
    //    private List<User> users;



}



