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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));//AQUI DA ERROR
        if(userService.getUser(user.getUser()) != null){
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> roles = new ArrayList<>();

        for(String role : user.getRoles()){
            roles.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return new org.springframework.security.core.userdetails.User(user.getUser(),user.getPassword(),roles);

    }
}
