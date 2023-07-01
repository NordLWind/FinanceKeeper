package ru.kostin.financekeeper.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/api/user/*").permitAll()
                .antMatchers("/api/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                .anyRequest().authenticated();
        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/login")
                .loginProcessingUrl("/do-login")
                .defaultSuccessUrl("/");

        http.logout()
                .logoutSuccessUrl("/sign-in")
                .logoutUrl("/logout");
    }
}
