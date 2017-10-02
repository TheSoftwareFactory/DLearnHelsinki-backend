package org.dlearn.helsinki.skeleton;

import org.dlearn.helsinki.skeleton.database.Database;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Database db = new Database();

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(db)
            .passwordEncoder(new BCryptPasswordEncoder(16));

        auth.inMemoryAuthentication()
            .withUser("student").password("password").roles("STUDENT")
            .and()
            .withUser("teacher").password("password").roles("TEACHER");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/webapi").authenticated()
            .antMatchers("/webapi/students/**").hasRole("TEACHER")
            .antMatchers("/webapi/teachers/**").hasRole("TEACHER")
            .anyRequest().authenticated()
            .and()
            .httpBasic();
    }
}