package com.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Order(1)
@Configuration
@EnableWebSecurity
//http://blog.florian-hopf.de/2017/08/spring-security.html
//https://www.programcreek.com/java-api-examples/index.php?api=org.springframework.security.provisioning.InMemoryUserDetailsManager
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Order(1)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests().antMatchers("/").authenticated()
                .and()
                .authorizeRequests().antMatchers("/admin/**").hasRole("admin")
                .and()
                .authorizeRequests().antMatchers("/cadeias/**","/users/**", "/reclusos/**", "/documents/**").authenticated()
                .and()
                .authorizeRequests().antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .and().csrf().disable();;
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("Administrador").password("123").roles("admin").build());
        manager.createUser(User.withDefaultPasswordEncoder().username("Osvanildo").password("123").roles("user").build());
        manager.createUser(User.withDefaultPasswordEncoder().username("Hamilton").password("123").roles("user").build());
//        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("123").roles("user").build());

        return manager;
    }
}