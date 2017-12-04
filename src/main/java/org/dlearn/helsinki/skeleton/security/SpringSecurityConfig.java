package org.dlearn.helsinki.skeleton.security;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import org.dlearn.helsinki.skeleton.database.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Database db = new Database();

    @Value("${security.signing-key}")
    private String signingKey;

    @Value("${security.encoding-strength}")
    private Integer encodingStrength;

    @Value("${security.security-realm}")
    private String securityRealm;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
    
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new ShaPasswordEncoder(encodingStrength));
    
        auth.jdbcAuthentication().dataSource(db.getDataSource())
                .usersByUsernameQuery("select * from ("
                        + "select username as username, pwd, 'true' as enabled from public.\"Students\""
                        + " union "
                        + "select username as username, pwd, 'true' as enabled from public.\"Teachers\""
                        + " union "
                        + "select username as username, pwd, 'true' as enabled from public.\"Researchers\""
                        + ") A where username=?")
                .authoritiesByUsernameQuery("select * from ("
                        + "select username as username, 'ROLE_STUDENT' as role from public.\"Students\""
                        + " union "
                        + "select username as username, 'ROLE_TEACHER' as role from public.\"Teachers\""
                        + " union "
                        + "select username as username, 'ROLE_RESEARCHER' as role from public.\"Researchers\""
                        + ") A where username=?")
                .passwordEncoder(Hasher.getHasher());
    }
    **/

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(Hasher.getHasher()); // still plain text
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .httpBasic().realmName(securityRealm).and().csrf().disable();

    }
    /*
        protected void configure(HttpSecurity http) throws Exception {
        //Implementing Token based authentication in this filter
        final TokenAuthenticationFilter tokenFilter = new TokenAuthenticationFilter();
        http.addFilterBefore((Filter) tokenFilter,
                BasicAuthenticationFilter.class);
    
        //Creating token when basic authentication is successful and the same token can be used to authenticate for further requests
        final CustomBasicAuthenticationFilter customBasicAuthFilter = new CustomBasicAuthenticationFilter(
                this.authenticationManager());
        http.addFilter(customBasicAuthFilter);
    
        http.csrf().disable().cors()
                .configurationSource((HttpServletRequest request) -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.applyPermitDefaultValues();
                    return cors;
                }).and().authorizeRequests().antMatchers("/").authenticated()
                .antMatchers("/webapi").authenticated()
                .antMatchers("/webapi/students/**")
                .hasAnyRole("TEACHER", "STUDENT")
                .antMatchers("/webapi/teachers/**").hasAnyRole("TEACHER")
                .antMatchers("/webapi/researcher/**").hasAnyRole("RESEARCHER")
                .and().httpBasic();
    }
    */

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    @Primary //Making this primary to avoid any accidental duplication with another token service instance of the same name
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
}