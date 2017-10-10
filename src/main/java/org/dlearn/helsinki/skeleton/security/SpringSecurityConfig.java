package org.dlearn.helsinki.skeleton.security;

import javax.servlet.http.HttpServletRequest;
import org.dlearn.helsinki.skeleton.database.Database;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Database db = new Database();

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        addTestAccounts(auth);
        auth.jdbcAuthentication().dataSource(db)
                .usersByUsernameQuery("select * from ("
                        + "select username as username, pwd, 'true' as enabled from public.\"Students\""
                        + " union "
                        + "select username as username, pwd, 'true' as enabled from public.\"Teachers\""
                        + ") A where username=?")
                .authoritiesByUsernameQuery("select * from ("
                        + "select username as username, 'ROLE_STUDENT' as role from public.\"Students\""
                        + " union "
                        + "select username as username, 'ROLE_TEACHER' as role from public.\"Teachers\""
                        + ") A where username=?")
                .passwordEncoder(Hasher.getHasher());
    }

    private void addTestAccounts(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication().withUser("teacher").password("password")
                .roles("TEACHER").and().withUser("student").password("password")
                .roles("STUDENT");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors()
                .configurationSource((HttpServletRequest request) -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.applyPermitDefaultValues();
                    return cors;
                }).and().authorizeRequests().antMatchers("/").authenticated()
                .antMatchers("/webapi").authenticated()
                .antMatchers("/webapi/students/**")
                .hasAnyRole("TEACHER", "STUDENT")
                .antMatchers("/webapi/teachers/**").hasAnyRole("TEACHER").and()
                .httpBasic();
    }
}