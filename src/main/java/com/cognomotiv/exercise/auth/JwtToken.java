package com.cognomotiv.exercise.auth;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtToken {
	
	private static final Logger s_logger=LogManager.getLogger(JwtToken.class);
	
	@Value("${secretKey}")
    private String secretKey;
	
	@Value("${timeLength}")
    private int timeLength;
    
    public String generate(String username) throws Exception {    	
        Calendar cal=Calendar.getInstance();
        Date now=cal.getTime();
        cal.add(Calendar.HOUR, timeLength);
        Date expire=cal.getTime();

        Map<String, Object> map=new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        return JWT.create().withHeader(map)
                .withClaim("iss", "Service")
                .withClaim("aud", "APP")
                .withClaim("username", username)
                .withIssuedAt(now)
                .withExpiresAt(expire)
                .sign(Algorithm.HMAC256(secretKey));
    }
    
    public boolean verifyToken(String username, String[] token) throws Exception {
    	if (username==null || "".equals(username) || token[0]==null || token[0].length()<8) {
    		return false;
    	}
    	try {
    		token[0]=token[0].substring(7);
            JWTVerifier verifier=JWT.require(Algorithm.HMAC256(secretKey)).build();
            DecodedJWT jwt=verifier.verify(token[0]);
            Map<String, Claim> claims=jwt.getClaims();
            if (claims==null) {
            	s_logger.info("Claims are null.");
        	    return false;
            }
        	return claims.containsKey("username") && username.equals(claims.get("username").asString());
    	} catch (Exception e) {
    		s_logger.info("Token verifiacation err="+e.getMessage());
    	}
    	return false;
    }
}
