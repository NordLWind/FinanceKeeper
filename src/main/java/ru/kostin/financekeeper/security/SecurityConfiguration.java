package ru.kostin.financekeeper.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/user/*").permitAll()
                .antMatchers("/api/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name());

        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/sign-in")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/");

        http.logout()
                .logoutSuccessUrl("/sign-in")
                .logoutUrl("/logout");
    }
}
