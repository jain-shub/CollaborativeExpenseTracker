/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.dao;

import com.projects.webdev.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Repository;

/**
 *
 * @author shubh
 */
@Repository
public class JwtTokenUtilImpl implements JwtTokenUtil, Serializable {
    
//    -2550185165626007488L
    private static final long serialVersionUID = 131606473482564709L;
        public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
//    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 24;
//    MoviesCornerSecretKeyMoviesCornerSecretKeyMoviesCornerSecretKey
    private final String secretKey = "SecretKeyCreatedforApplicationToProtectFishing";

    // get userId from jwt token
    @Override
    public String getUserIdFromToken(String token) {
        System.out.println("point 10");
        return getClaimFromToken(token, Claims::getSubject);
    }

    // get expiration date from jwt token
    @Override
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    @Override
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        System.out.println("point 11");
        return claimsResolver.apply(claims);
    }
    
    //for retrieveing any information from token we will need the secretKey key
    @Override
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
    
    //check if the token has expired
    @Override
    public boolean isTokenExpired(String token) {
        final Date exp = getExpirationDateFromToken(token);
        return exp.before(new Date());
    }
    
    //generate token for user
    @Override
    public String generateToken(User u) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, String.valueOf(u.getUserId()));
    }

    @Override
    public String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }
    
    //validate token
    @Override
    public boolean validateToken(String token, User userDetails) {
        final String userId = getUserIdFromToken(token);
        return (userId.equals(String.valueOf(userDetails.getUserId())) && !isTokenExpired(token));
    }

//    public static String generateToken(String id, String issuer, String subject, long ttlMillis) {
//		 //The JWT signature algorithm we will be using to sign the token
//	    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//
//	    long nowMillis = System.currentTimeMillis();
//	    Date now = new Date(nowMillis);
//
//	    //We will sign our JWT with our ApiKey secret
//	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("SECRET");
//	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
//
//	    //Let's set the JWT Claims
//	    JwtBuilder builder = Jwts.builder().setId(id)
//	                                .setIssuedAt(now)
//	                                .setSubject(subject)
//	                                .setIssuer(issuer)
//	                                .signWith(signatureAlgorithm, signingKey);
//
//	    //if it has been specified, let's add the expiration
//	    if (ttlMillis >= 0) {
//	    long expMillis = nowMillis + ttlMillis;
//	        Date exp = new Date(expMillis);
//	        builder.setExpiration(exp);
//	    }
//
//	    //Builds the JWT and serializes it to a compact, URL-safe string
//	    return builder.compact();
//
//	}
//	
//	public static Claims parseJWT(String jwt) {
//		 
//	    //This line will throw an exception if it is not a signed JWS (as expected)
//	    Claims claims = Jwts.parser()         
//	       .setSigningKey(DatatypeConverter.parseBase64Binary("SECRET"))
//	       .parseClaimsJws(jwt).getBody();
//	    return claims;
//	}

}
