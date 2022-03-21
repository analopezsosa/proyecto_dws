package com.example.proyecto_dws;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String user;
    private String password;
    private List<String> gradesOfTheUser = new ArrayList<>();

    public User(String user, String password){
        this.user=user;
        this.password = password;
    }

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
    }

}
