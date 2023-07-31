package com.axis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.axis.filter.JwtFilter;
import com.axis.service.UserDetailsServiceImpl;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsServiceImpl);
	}
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.cors().and()
			.csrf()
			.disable()
			.authorizeRequests()
			.antMatchers("/customer/register","/admin/register-employee","/customer/authenticate","/employee/authenticate","/admin/authenticate","/employee/forgot-password","/employee/reset-password","/customer/forgot-password","/customer/reset-password","/customer/unblock","/employee/unblock")
			.permitAll()
			.antMatchers("/employee/home","/employee/profile","/employee/pending-accounts","/employee/activate-account","/employee/all-accounts","/employee/delete-customer","/employee/edit/{id}")
			.hasRole("EMPLOYEE")
			.antMatchers("/customer/home","/customer/profile","/customer/edit/{id}")
			.hasRole("CUSTOMER")
			.antMatchers("/admin/home","/admin/profile","/admin/all-employees","/admin/delete-employee")
			.hasRole("ADMIN")
			.anyRequest()
			.authenticated()
			.and()
			.exceptionHandling()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
//		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	public FilterRegistrationBean coresFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		
//		CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.setAllowCredentials(true);
//		corsConfiguration.addAllowedOriginPattern("*");
//		corsConfiguration.addAllowedHeader("Authorization");
//		corsConfiguration.addAllowedHeader("Content-Type");
//		corsConfiguration.addAllowedHeader("Accept");
//		corsConfiguration.addAllowedMethod("POST");
//		corsConfiguration.addAllowedMethod("GET");
//		corsConfiguration.addAllowedMethod("DELETE");
//		corsConfiguration.addAllowedMethod("PUT");
//		corsConfiguration.addAllowedMethod("OPTIONS");
//		corsConfiguration.setMaxAge(3600L);
//		
//		source.registerCorsConfiguration("/**", corsConfiguration);
//		
//		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//	
//		return bean;
//		
//	}
}
