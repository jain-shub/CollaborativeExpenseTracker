/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.exception;

import java.util.List;

/**
 *
 * @author shubh
 */
public class Errors {
    private List<Message> errors;
    
    public Errors(List<Message> e) {
        this.errors = e;
    }

    public List<Message> getError() {
        return errors;
    }

    public void setError(List<Message> error) {
        this.errors = error;
    }
}
