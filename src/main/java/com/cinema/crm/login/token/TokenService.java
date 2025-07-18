package com.cinema.crm.login.token;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cinema.crm.constants.Constants;
import com.cinema.crm.constants.Constants.Result;
import com.cinema.crm.databases.pvrinoxcrm.entities.Users;
import com.cinema.crm.databases.pvrinoxcrm.repositories.RoleRepository;
import com.cinema.crm.login.model.LoggedInResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
/**
 * This class is use specify the token behavior.
 * @author sazzad.alom
 * @version 1.0.0
 * @since 10-07-2025
 *
 */
@Service
public class TokenService {
	

	@Value("${expiry-time}")
	private int exparyTime;
	private SecretKey secretKey ;
	@Autowired private RoleRepository roleRepository;
	
	
	/**
     * Initialize the secret key after the bean is created.
     */
    @PostConstruct
    public void init() {
        try {
        	secretKey = KeyGenerator.getInstance("HmacSHA256").generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error initializing SecretKey for JWT", e);
        }
    }

	public LoggedInResponse generateToken(Users user) {
		Map<String, Object> claims = new HashMap<>();

		Date now = new Date(System.currentTimeMillis());
		Date exp = new Date(System.currentTimeMillis() + exparyTime * 60 * 1000);
		
				String token = Jwts.builder().claims().add(claims).subject(user.getEmail()).issuedAt(now).expiration(exp).and().signWith(secretKey).compact();
				
				return LoggedInResponse.builder().result(Result.SUCCESS)
						.responseCode(Constants.RespCode.SUCCESS)
						.message(Constants.Message.LOGIN_SUCCESSFULLY)
						.username(user.getName())
						.email(user.getEmail())
						.mobile(user.getMobile())
						.profile(user.getRole().substring(5))
						.modules(roleRepository.findByRoleName(user.getRole().substring(5)).getModules())
						.token(token)
						.issuedAt(now)
						.expiration(exp)
						.build();
	}

	/**
	 *  Extract userName from JWT token
	 *  
	 * @param token
	 * @return
	 */
	public String extractUsername(String token) {
		
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		
		return (username.equals(userDetails.getUsername()) && !isTokenExpaired(token));
	}

	private boolean isTokenExpaired(String token) {
		return extractExparation(token).before(new Date());
	}

	private Date extractExparation(String token) {
		
		return extractClaim(token, Claims::getExpiration);
	}


}
