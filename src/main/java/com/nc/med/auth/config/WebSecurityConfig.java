package com.nc.med.auth.config;

import com.nc.med.auth.jwt.AuthEntryPointJwt;
import com.nc.med.auth.jwt.AuthTokenFilter;
import com.nc.med.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = false,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers("/auth/signin/**").permitAll().and().authorizeRequests()
                .antMatchers("/", "/auth/**", "/category/**", "/product/**", "/h2-console/**",
                        "/customer/**", "/bank/**", "/location/**")
                .permitAll().and().authorizeRequests().antMatchers("/supplier/**").permitAll().and().authorizeRequests()
                .antMatchers("/balancesheet/**").permitAll().and().authorizeRequests().antMatchers("/purchaseOrder/**")
                .permitAll().and().authorizeRequests().antMatchers("/salesOrder/**").permitAll().and()
                .authorizeRequests().antMatchers("/company/**").permitAll().and().authorizeRequests()
                .antMatchers("/smart-accounting-book-backend/**").permitAll().and().authorizeRequests()
                .antMatchers("/actuator/**").permitAll().anyRequest().authenticated().and().csrf().disable().formLogin()
                .loginPage("/login").permitAll();

        // h2-console for authorizeRequests
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
