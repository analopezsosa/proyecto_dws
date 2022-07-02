package com.example.proyectodws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    RepositoryUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        //roles
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        String encodedPassword = encoder.encode("pass");
        auth.inMemoryAuthentication().withUser("user").password((encodedPassword)).roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password(encoder.encode("adminpass")).roles("USER","ADMIN");


        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }



    @Override
    protected  void configure(HttpSecurity http) throws Exception{

        //public
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/signup").permitAll();
        http.authorizeRequests().antMatchers("/error").permitAll();

/*
        // Private pages (all other pages)

        http.authorizeRequests().antMatchers("/joingradeU").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/removeuserfromgrade").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/viewuser/{user}").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/filter").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/removeUser").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/viewusers").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/editsubject.html").hasAnyRole("ADMIN");

        http.authorizeRequests().antMatchers("/addsubjecttograde").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/removesubject.html").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/removesubjectfromgrade").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/createsubject.html").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/creategrade").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/removegrade").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/removeUsers").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/editgrade").hasAnyRole("ADMIN");
        http.authorizeRequests().antMatchers("/editsubject.html").hasAnyRole("ADMIN");

        http.authorizeRequests().anyRequest().authenticated();
*/
        // Disable CSRF at the moment
//http.csrf().disable();

        //login form
        http.formLogin().loginPage("/http://localhost:8080/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/functionalities");
        //http.formLogin().failureUrl("/error");

        http.logout().logoutSuccessUrl("/");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(15, new SecureRandom());
    }





}
