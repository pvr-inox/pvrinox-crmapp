package com.cinema.crm.login.configuration;

import java.util.LinkedList;
import java.util.List;

import com.cinema.crm.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cinema.crm.login.configuration.filter.TokenFilter;
import com.cinema.crm.login.service.ApplicationUserDetailService;

/**
 * Configuration class for setting up Spring Security in the Cinema CRM application.
 *
 * <p>This class defines the core security components including the authentication
 * manager, security filter chain, and authentication provider. It integrates
 * JWT-based authentication using a custom {@link TokenFilter}, and sets up
 * role-based access control for different API endpoints.</p>
 *
 * <p>Key features:
 * <ul>
 *   <li>Disables CSRF protection (suitable for stateless REST APIs).</li>
 *   <li>Configures public and secured API endpoints with fine-grained authority checks.</li>
 *   <li>Registers a {@link DaoAuthenticationProvider} that uses {@link BCryptPasswordEncoder} for secure password hashing.</li>
 *   <li>Injects a custom user details service {@link ApplicationUserDetailService} for loading user credentials.</li>
 *   <li>Applies a JWT filter to validate tokens before processing authentication requests.</li>
 * </ul>
 * </p>
 *
 * <p>Roles handled:
 * <ul>
 *   <li><b>ROLE_SUPER_ADMIN</b> - Allowed to access registration endpoints.</li>
 *   <li><b>ROLE_CRM_EXECUTIVE</b> - Allowed to initiate refunds.</li>
 *   <li><b>ROLE_NODAL_OFFICER</b> - Allowed to approve refunds.</li>
 * </ul>
 * </p>
 *
 * @author
 *   Sazzad.Alom
 * @version
 *   1.0.0
 * @since
 *   2025-07-09
 */

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Autowired
	private ApplicationUserDetailService userDetailService;

	@Autowired
	private TokenFilter tokenFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http
		.csrf(customizer -> customizer.disable())
		.cors(Customizer.withDefaults())
		.authorizeHttpRequests(customizer -> customizer
				.requestMatchers(defaultPermissions().toArray(String[]::new)).permitAll()
			    .requestMatchers(userCreator().toArray(String[]::new)).hasAuthority(Constants.UserRoles.SUPER_ADMIN)
			    .requestMatchers(executive().toArray(String[]::new)).hasAuthority(Constants.UserRoles.EXECUTIVE)
			    .requestMatchers(officer().toArray(String[]::new)).hasAuthority(Constants.UserRoles.OFFICER)
				.anyRequest().authenticated())
		.httpBasic(Customizer.withDefaults())
		.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
		.build();
	}
	
	/**
	 * <p>Create authentication provider bean for authentication manager.</p>
	 * @return
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		provider.setUserDetailsService(userDetailService);
		return provider;
	}

	/**
	 * <p>Use for manage authentication object here</p>
	 * @param config
	 * @return
	 * @throws Exception
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	} 
	
	
	private List<String> executive(){
		List<String> permittedApis = new LinkedList<>();
		permittedApis.add("/api/v1/refund/initiate-refund");
		permittedApis.add("/api/v1/manage/**");
		return permittedApis;
		
	}
	
	private List<String> officer(){
		List<String> permittedApis = new LinkedList<>();
		permittedApis.add("/api/v1/refund/approve-refund");
		return permittedApis;
		
	}
	private List<String> defaultPermissions(){
		 return List.of(
				 "/",
				 "/actuator/health",
				 "/public/**",
			        "/api/auth/login",
			        "/api/auth/refresh",
			        "/swagger-ui.html",
			        "/swagger-ui/**",
			        "/v3/api-docs/**",
			        "/api/v1/show/save"
			    );
	}


	private List<String> userCreator(){
		List<String> permittedApis = new LinkedList<>();
		permittedApis.add("/api/auth/register");
		permittedApis.add("/api/v1/user/**");
		permittedApis.add("/api/v1/refund/approve-refund");
		permittedApis.add("/api/v1/show/save");
		permittedApis.add("/api/v1/refund/initiate-refund");
		permittedApis.add("/api/v1/manage/**");
		return  permittedApis;
	}
}
