package jwtphoneno.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	 public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


	    public Boolean validateToken(final String token) {
	        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
			return true;
	    }


	/*    public String generateToken(String phoneno) {
	        Map<String, Object> claims = new HashMap<>();
	        return createToken(claims, phoneno);
	    }

	    private String createToken(Map<String, Object> claims, String phoneno) {
	        return Jwts.builder()
	                .setClaims(claims)
	                .setSubject(phoneno)
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
	                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	    }  */
	    
	    public String generateToken(String email) {
	        Map<String, Object> claims = new HashMap<>();
	        return createToken(claims, email);
	    }

	    private String createToken(Map<String, Object> claims, String email) {
	        return Jwts.builder()
	                .setClaims(claims)
	                .setSubject(email)
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
	                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	    }


	    private Key getSignKey() {
	        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }
	    
	    public String getUsernameFromToken(String token) {
	        return getClaimFromToken(token, Claims::getSubject);
	    }

	    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = getAllClaimsFromToken(token);
	        return claimsResolver.apply(claims);
	    }
	    
	    private Claims getAllClaimsFromToken(String token) {
	        return Jwts.parser().setSigningKey(getSignKey()).parseClaimsJws(token).getBody();
	    }
	    
}
