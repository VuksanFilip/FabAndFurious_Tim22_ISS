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
import java.util.Date;

@Component
public class TokenUtils {

	@Value("spring-security-example")
	private String APP_NAME;

	@Value("somesecret")
	public String SECRET;

	@Value("1800000")
	private int EXPIRES_IN;
	
	@Value("Authorization")
	private String AUTH_HEADER;
	
	private static final String AUDIENCE_WEB = "web";

	private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

	public String generateToken(String username) {
		return Jwts.builder()
				.setIssuer(APP_NAME)
				.setSubject(username)
				.setAudience(generateAudience())
				.setIssuedAt(new Date())
				.setExpiration(generateExpirationDate())
				.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
	}

	private String generateAudience() {
		
		//	Moze se iskoristiti org.springframework.mobile.device.Device objekat za odredjivanje tipa uredjaja sa kojeg je zahtev stigao.
		//	https://spring.io/projects/spring-mobile
				
		//	String audience = AUDIENCE_UNKNOWN;
		//		if (device.isNormal()) {
		//			audience = AUDIENCE_WEB;
		//		} else if (device.isTablet()) {
		//			audience = AUDIENCE_TABLET;
		//		} else if (device.isMobile()) {
		//			audience = AUDIENCE_MOBILE;
		//		}
		
		return AUDIENCE_WEB;
	}

	private Date generateExpirationDate() {
		return new Date(new Date().getTime() + EXPIRES_IN);
	}

	public String getToken(HttpServletRequest request) {
		String authHeader = getAuthHeaderFromHeader(request);

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}

		return null;
	}

	public String getUsernameFromToken(String token) {
		String username;
		
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			username = claims.getSubject();
		} catch (ExpiredJwtException ex) {
			throw ex;
		} catch (Exception e) {
			username = null;
		}
		
		return username;
	}

	public Date getIssuedAtDateFromToken(String token) {
		Date issueAt;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			issueAt = claims.getIssuedAt();
		} catch (ExpiredJwtException ex) {
			throw ex;
		} catch (Exception e) {
			issueAt = null;
		}
		return issueAt;
	}

	public String getAudienceFromToken(String token) {
		String audience;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			audience = claims.getAudience();
		} catch (ExpiredJwtException ex) {
			throw ex;
		} catch (Exception e) {
			audience = null;
		}
		return audience;
	}

	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (ExpiredJwtException ex) {
			throw ex;
		} catch (Exception e) {
			expiration = null;
		}
		
		return expiration;
	}

	private Claims getAllClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException ex) {
			throw ex;
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		User user = (User) userDetails;
		final String username = getUsernameFromToken(token);
		final Date created = getIssuedAtDateFromToken(token);
		
		return (username != null // korisnicko ime nije null
			&& username.equals(userDetails.getUsername())); // korisnicko ime iz tokena se podudara sa korisnickom imenom koje pise u bazi
//			&& !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())); // nakon kreiranja tokena korisnik nije menjao svoju lozinku
	}

	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	public int getExpiredIn() {
		return EXPIRES_IN;
	}

	public String getAuthHeaderFromHeader(HttpServletRequest request) {
		return request.getHeader(AUTH_HEADER);
	}
	
}