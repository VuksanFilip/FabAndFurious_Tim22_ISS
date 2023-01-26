package rs.ac.uns.ftn.informatika.jpa.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class TokenUtils implements Serializable {

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	public static final long JWT_REFRESH_TOKEN_VALIDITY = 24*60*60;

	@Value("${jwt.secret}")
	private String secret;

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}


	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

//	public String getRoleFromToken(String token) {
//		Map<String, Object> claims;
//		claims = getAllClaimsFromToken(token);
//		return (String) claims.get("role");
//	}
//
//	public int getUserIdFromToken(String token) {
//		Map<String, Object> claims;
//		claims = getAllClaimsFromToken(token);
//		return (int) claims.get("id");
//	}

	private Boolean isTokenExpired(String token){
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(User userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", userDetails.getRole());
		claims.put("id", userDetails.getId());
		return doGenerateToken(claims, userDetails.getEmail());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public String generateRefreshToken(User userDetails) {
		Map<String,Object> claims = new HashMap<>();
		claims.put("role", userDetails.getRole());
		claims.put("id", userDetails.getId());
		return doGenerateRefreshToken(claims, userDetails.getEmail());
	}

	private String doGenerateRefreshToken(Map<String, Object> claims, String email) {
		return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+JWT_REFRESH_TOKEN_VALIDITY*1000))
				.signWith(SignatureAlgorithm.HS512,secret).compact();
	}


	public Boolean validateToken(String token, User userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getEmail()) && !isTokenExpired(token) && !userDetails.isBlocked());
	}
	
}