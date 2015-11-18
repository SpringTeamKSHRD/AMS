package com.ams.app.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {		
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("SELECT username,password,enable FROM tbuser WHERE username=?")
			.authoritiesByUsernameQuery("SELECT username,role "
										+ "FROM tbuser a "
										+ "INNER JOIN tbuser_role b ON a.id = b.user_id "
										+ "INNER JOIN tbrole c ON b.id = c.id "
										+ "WHERE username = '?' ");		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			//.httpBasic()
			//.and()
			.formLogin()
                  .loginPage("/login")
               	 // .successHandler(successHandler)
              //  .usernameParameter("username")
              //  .passwordParameter("password")
			.and().authorizeRequests()
				//.antMatchers("/").permitAll()
				.antMatchers("/resources/**").permitAll()
				
				//.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/api/admin/**").hasRole("ADMIN")
				.antMatchers("/author/**").hasRole("AUTHOR")
				.antMatchers("/api/author/**").hasRole("AUTHOR")
				.antMatchers("/api/**").permitAll()
				.antMatchers("/login").hasRole("ANONYMOUS")
			.and().csrf().disable();						
		//http.exceptionHandling().accessDeniedPage("/403");
	}
	
}