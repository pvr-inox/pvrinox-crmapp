package com.cinema.crm.login.configuration.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cinema.crm.login.token.TokenService;

import io.jsonwebtoken.ExpiredJwtException;

import com.cinema.crm.login.service.ApplicationUserDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/**
 * <p>The filter implementation class is use to verify with jwt token </p>
 * @author sazzad.alom
 * @version 1.0.0
 * @since 09-07-2025
 *
 */
@Log4j2
@Component
public class TokenFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private ApplicationContext context;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, ExpiredJwtException {
		
		String authHeader = request.getHeader("Authorization");
		log.debug("authHeader: {}", authHeader);

		
		String token = null;
		String username = null;
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			try {
				username = tokenService.extractUsername(token);

			} catch (io.jsonwebtoken.ExpiredJwtException e) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.getWriter().write("Token expired");
				return;

			} catch (io.jsonwebtoken.security.SignatureException e) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.getWriter().write("Invalid token signature");
				return;

			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.getWriter().write("Invalid token");
				return;
			}
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = context.getBean(ApplicationUserDetailService.class).loadUserByUsername(username);
			log.debug("userDetails: {}", userDetails.toString());

			if (tokenService.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}

		filterChain.doFilter(request, response);
	}

}
