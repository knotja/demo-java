package com.okane.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.okane.domain.services.MyUserAuthenticationServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	

	@Autowired
	private MyUserAuthenticationServiceImpl userAuthService;

	@Autowired
	public void configureUserAuth(AuthenticationManagerBuilder auth) throws Exception {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		auth.userDetailsService(userAuthService).passwordEncoder(encoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/static/**", "/css/**", "/fonts/**", "/images/**", "/js/**", "/sass/**", "/login",
						"/register", "/register/add","/")
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login")
				.defaultSuccessUrl("/dashboard", true).usernameParameter("email").permitAll().and().logout()
				.permitAll();
	}
}