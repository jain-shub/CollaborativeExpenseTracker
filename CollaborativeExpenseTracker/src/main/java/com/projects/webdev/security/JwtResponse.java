/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.security;

import java.io.Serializable;

/**
 *
 * @author shubh
 */
public class JwtResponse implements Serializable{
    private static final long serialVersionUID  = -8091879091924046844L;
    private final String jwttoken;
    
    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }
    
    public String getToken() {
        return this.jwttoken;
    }
}