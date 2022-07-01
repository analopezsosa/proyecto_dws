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
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/signup").permitAll();
        http.authorizeRequests().antMatchers("/error").permitAll();

/*
        // Private pages (all other pages)

        http.authorizeRequests().antMatchers("/private").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/admin").hasAnyRole("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        // Disable CSRF at the moment
http.csrf().disable();
*/
        //login form
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/functionalities");
        http.formLogin().failureUrl("/error");

        http.logout().logoutSuccessUrl("/");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(15, new SecureRandom());
    }





}
