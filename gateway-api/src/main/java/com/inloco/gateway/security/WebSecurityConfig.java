package com.inloco.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@ComponentScan
@EnableAutoConfiguration
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomAuthenticationManager customAuth;
	@Autowired
	private UserDetailsService userDetailsService;
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
		auth.authenticationProvider(authProvider());
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.headers().frameOptions().disable();
		
		httpSecurity.csrf().disable().authorizeRequests()
			.antMatchers("/users/singup").permitAll()
			.antMatchers("/h2-console/**").permitAll()
			.antMatchers("/swagger-ui.html").permitAll()
			.antMatchers(HttpMethod.POST, "/users/singin").permitAll()
			.anyRequest().authenticated()
			.and()
			
			.addFilterBefore(new JWTLoginFilter("/users/singin", customAuth),
	                UsernamePasswordAuthenticationFilter.class)
			
			.addFilterBefore(new JWTAuthorizationFilter(customAuth),
	                UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	private BCryptPasswordEncoder passwordEncoder() {
	    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	    return bCryptPasswordEncoder;
	}
}
