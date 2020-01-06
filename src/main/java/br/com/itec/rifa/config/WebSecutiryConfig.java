package br.com.itec.rifa.config;

import br.com.itec.rifa.filters.JwtAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

@Component
@EnableWebSecurity
public class WebSecutiryConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().configurationSource( request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .csrf().disable().authorizeRequests()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/user/register").permitAll()
                .and()
                .addFilterAfter(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
