package com.thesisSpringApp.filter;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.thesisSpringApp.JwtComponents.JwtService;
import com.thesisSpringApp.pojo.User;
import com.thesisSpringApp.service.UserService;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

	private final static String TOKEN_HEADER = "authorization";
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserService userService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader(TOKEN_HEADER);
		try {
			if (jwtService.validateTokenLogin(authToken)) {
				String username = jwtService.getUsernameFromToken(authToken);
				User user = userService.getUserByUsername(username);
				if (user != null) {
					boolean enabled = true;
					boolean accountNonExpired = true;
					boolean credentialsNonExpired = true;
					boolean accountNonLocked = true;

					Set<GrantedAuthority> authorities = new HashSet<>();
					authorities.add(new SimpleGrantedAuthority(user.getRoleId().getName()));

					UserDetails userDetail = new org.springframework.security.core.userdetails.User(
							username, user.getPassword(), enabled, accountNonExpired,
							credentialsNonExpired, accountNonLocked, authorities);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetail,
							null, userDetail.getAuthorities());
					authentication
							.setDetails(
									new WebAuthenticationDetailsSource().buildDetails(httpRequest));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chain.doFilter(request, response);
	}
}
