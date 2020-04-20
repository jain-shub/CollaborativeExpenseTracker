/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.dao;

import com.projects.webdev.model.User;
import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author shubh
 */
public interface JwtTokenUtil {
    public String getUserIdFromToken(String token);

    // get expiration date from jwt token
    public Date getExpirationDateFromToken(String token);

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);
    
    //for retrieveing any information from token we will need the secretKey key
    public Claims getAllClaimsFromToken(String token);
    
    //check if the token has expired
    public boolean isTokenExpired(String token);
    
    //generate token for user
    public String generateToken(User u);

    public String doGenerateToken(Map<String, Object> claims, String subject);
    
    //validate token
    public boolean validateToken(String token, User userDetails);
}
