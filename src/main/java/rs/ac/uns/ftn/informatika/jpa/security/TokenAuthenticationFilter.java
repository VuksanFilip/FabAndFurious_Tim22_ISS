package rs.ac.uns.ftn.informatika.jpa.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IUserService;
import rs.ac.uns.ftn.informatika.jpa.util.TokenUtils;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private IUserService userDetails;
	@Autowired
	private TokenUtils jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (request.getRequestURL().toString().contains("/api/")) {
			System.out.println("####" + request.getMethod() + ":" + request.getRequestURL());
			System.out.println("#### Authorization: " + request.getHeader("Authorization"));
			System.out.println(request.getAuthType());
			String requestTokenHeader = request.getHeader("Authorization");
			String username = null;
			String jwtToken = null;
			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
				jwtToken = requestTokenHeader.substring(requestTokenHeader.indexOf("Bearer ") + 7);
				System.out.println(">>>>>JWT TOKEN: " + jwtToken);
				try {
					username = jwtTokenUtil.getUsernameFromToken(jwtToken);
					UserDetails userDetails = this.userDetails.loadUserByUsername(username);
					if (jwtTokenUtil.validateToken(jwtToken, (User) userDetails)) {
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						usernamePasswordAuthenticationToken
								.setDetails(new
										WebAuthenticationDetailsSource().buildDetails(request));
						System.out.println("Username: " + userDetails.getUsername() + ", role: " + userDetails.getAuthorities());
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					}
				} catch (IllegalArgumentException e) {
					System.out.println("Unable to get JWT Token.");
				} catch (ExpiredJwtException e) {
					System.out.println("JWT Token has expired.");
				} catch (io.jsonwebtoken.MalformedJwtException e) {
					System.out.println("Bad JWT Token.");
				}
			} else {
				logger.warn("JWT Token does not exist.");
			}
		}
		filterChain.doFilter(request, response);
	}

}