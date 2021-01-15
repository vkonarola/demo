package com.narola.authenticationservice.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.narola.authenticationservice.config.jwt.JwtAuthTokenFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("custom_userdetail_service")
	private UserDetailsService customUserDetailsService;

	@Value("${app.easycollab.authUrl}")
	private String narolaAuthUrl;

	@Autowired
	private JwtAuthTokenFilter jwtAuthTokenFilter;

	@Autowired
	private RestAuthenticationEntryPoint unauthorizedHandler;

	@Override
	public void configure(WebSecurity web) throws Exception {
		// @formatter:off
        web.ignoring().antMatchers("/v2/api-docs",
                                   "/configuration/**",
                                   "/swagger-resources/**",                                   
                                   "/swagger-ui.html",
                                   "/webjars/**");
        // @formatter:on
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.cors().and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.authorizeRequests()		
			.antMatchers("/api/auth/signup/**").permitAll()
			.antMatchers("/api/auth/login/**").permitAll()
			.antMatchers("/api/test/**").permitAll()
			.antMatchers("/swagger-ui/**").permitAll()
			.anyRequest().authenticated()
		.and()
			.formLogin().disable()
			.csrf().disable()
			.exceptionHandling().authenticationEntryPoint(this.unauthorizedHandler)
		.and()
			.addFilterBefore(this.jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
		// @formatter:on
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		corsConfiguration.setMaxAge(Duration.ofDays(10));
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(this.customUserDetailsService).passwordEncoder(passwordEncoder());
		auth.authenticationProvider(narolaSystemAuthProvider()).eraseCredentials(true);
	}

	@Bean
	public NarolaSystemAuthProvider narolaSystemAuthProvider() {
		NarolaSystemAuthProvider narolaSystemAuthProvider = new NarolaSystemAuthProvider();
		narolaSystemAuthProvider.setLoginUrl(this.narolaAuthUrl);
		return narolaSystemAuthProvider;
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
}