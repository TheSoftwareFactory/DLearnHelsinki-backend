
package org.dlearn.helsinki.skeleton.security;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private ResourceServerTokenServices tokenServices;

    @Value("${security.jwt.resource-ids}")
    private String resourceIds;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources)
            throws Exception {
        resources.resourceId(resourceIds).tokenServices(tokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors()
                .configurationSource((HttpServletRequest request) -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.applyPermitDefaultValues();
                    return cors;
                }).and().authorizeRequests()
                .antMatchers("/actuator/**", "/api-docs/**").permitAll() //copypasterino
                .antMatchers("/webapi").authenticated()
                .antMatchers("/webapi/students/**")
                .hasAnyRole("TEACHER", "STUDENT")
                .antMatchers("/webapi/teachers/**").hasAnyRole("TEACHER")
                .antMatchers("/webapi/researcher/**").hasAnyRole("RESEARCHER")
                .and().httpBasic();
    }
}