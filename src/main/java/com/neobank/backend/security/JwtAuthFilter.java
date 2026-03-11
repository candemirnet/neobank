package com.neobank.backend.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException,
			IOException {
		
		
		final String header = request.getHeader("Authorization");
		
		if(header == null || !header.startsWith("Bearer ")) {
			
			filterChain.doFilter(request, response);
			return;
			
		}
		
		final String token = header.substring(7);
		
		try {
			
			final String username = jwtUtil.extractUsername(token);
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				
				if(userDetails!= null && jwtUtil.isTokenValid(token, userDetails)) {
					
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken
																		(username, null, userDetails.getAuthorities());
					authToken.setDetails(userDetails);
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}	
			}
			
		} catch (Exception e) {
			 // Geçersiz token — filtre zinciri devam eder, istek korumalı endpoint'e ulaşamaz
		}
		
		filterChain.doFilter(request, response);
		
		
	}

}
