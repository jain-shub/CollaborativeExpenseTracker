/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.exception;

/**
 *
 * @author shubh
 */
public class ExpenseException extends Exception {

	public ExpenseException(String message) {
		super("ExpenseException-" + message);
	}
}
