package com.example.proyecto_dws;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class User {
    private String user;
    private String password;


    public User(String user, String password){
        this.user=user;
        this.password = password;
    }

    public boolean isPasswordCorrect (String passwordEntered){
        return this.password.equals(passwordEntered);
    }

    public String getUser() {
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

}
