/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author shubh
 */
@Entity(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private long userId;

    private String password;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "fromUser", cascade = {CascadeType.MERGE})
    private Collection<Expenses> dueExpenses;

    @JsonIgnore
//    , fetch = FetchType.EAGER
    @OneToMany(mappedBy = "toUser", cascade = {CascadeType.MERGE})
    private Collection<Expenses> owedExpenses;

    private double amountDue;
    private double amountOwed;

    @JsonIgnore
//    ,fetch = FetchType.EAGER
    @OneToMany(mappedBy = "members", cascade = {CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Groups> grouplist = new HashSet<>();

    public User() {

    }

//    ArrayList<Expenses> dueExpenses, ArrayList<Expenses> owedExpenses, 
    public User(long userId, String password, String firstName, String lastName, String email, double amountdue, double amountOwed) {
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dueExpenses = new HashSet<>();
        this.owedExpenses = new HashSet<>();
        this.amountDue = amountdue;
        this.amountOwed = amountOwed;
        this.grouplist = new HashSet<>();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Expenses> getDueExpenses() {
        return dueExpenses;
    }

    public void setDueExpenses(Set<Expenses> dueExpenses) {
        this.dueExpenses = dueExpenses;
    }

    public Collection<Expenses> getOwedExpenses() {
        return owedExpenses;
    }

    public void setOwedExpenses(Set<Expenses> owedExpenses) {
        this.owedExpenses = owedExpenses;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public double getAmountOwed() {
        return amountOwed;
    }

    public void setAmountOwed(double amountOwed) {
        this.amountOwed = amountOwed;
    }

    public Collection<Groups> getGrouplist() {
        return grouplist;
    }

    public void setGrouplist(Collection<Groups> grouplist) {
        this.grouplist = grouplist;
    }

    @Override
    public String toString() {
        return "User [id = " + userId + ", firstName = " + firstName + ", lastName = " + lastName + ", email = " + email + ", amount due = " + amountDue + ", amount owed" + amountOwed + "]";
    }
}
