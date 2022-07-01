package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//interfaz con caracteristicas de las cuentas de usuarios
@Service
public class RepositoryUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException{
        User username = userRepository.findByUser(user).orElseThrow(() -> new UsernameNotFoundException("User not found"));//AQUI DA ERROR
        if(userService.getUser(username.getUser()) != null){
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> roles = new ArrayList<>();

        for(String role : username.getRoles()){
            roles.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return new org.springframework.security.core.userdetails.User(username.getUser(),username.getPassword(),roles);

    }
}
