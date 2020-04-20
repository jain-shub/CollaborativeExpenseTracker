/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

/**
 *
 * @author shubh
 */
@Embeddable
@Entity(name = "UserExpense")
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

//    @Enumerated(EnumType.STRING)
//    private ExpenseCategory category;
    private double amount;
    
    @JsonIgnore
    @OneToOne(cascade = {CascadeType.MERGE})
//    @JoinColumn(name="fromUser", referencedColumnName="userId")
    @JoinTable(name="FromUser_UserExpense", joinColumns={@JoinColumn(referencedColumnName="id")}
                                        , inverseJoinColumns={@JoinColumn(referencedColumnName="userId")})
    private User fromUser;
    
    @JsonIgnore
    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinTable(name="ToUser_UserExpense", joinColumns={@JoinColumn(referencedColumnName="id")}
                                        , inverseJoinColumns={@JoinColumn(referencedColumnName="userId")})
//    @JoinColumn(name="toUser", referencedColumnName="userId")
    private User toUser;
    
    private String fromUserName;
    private String toUserName;
    private String type;
    private double fromUserSplitPercentageValue;
    private double toUserSplitPercentageValue;

    public Expenses() {

    }

//     ExpenseCategory category, 
    public Expenses(long id, String name, double amount, User fromUser, User toUser, String fromUserName, String toUserName, String expenseType, double fromUserSplitPercentageValue, double toUserSplitPercentageValue) {
        this.id = id;
        this.name = name;
//        this.category = category;
        this.amount = amount;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.fromUserName = fromUserName;
        this.toUserName = toUserName;
        this.type = expenseType;
        this.fromUserSplitPercentageValue = fromUserSplitPercentageValue;
        this.toUserSplitPercentageValue = toUserSplitPercentageValue;
    }

    

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public ExpenseCategory getCategory() {
//        return category;
//    }
//
//    public void setCategory(ExpenseCategory category) {
//        this.category = category;
//    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public double getFromUserSplitPercentageValue() {
        return fromUserSplitPercentageValue;
    }

    public void setFromUserSplitPercentageValue(double fromUserSplitPercentageValue) {
        this.fromUserSplitPercentageValue = fromUserSplitPercentageValue;
    }

    public double getToUserSplitPercentageValue() {
        return toUserSplitPercentageValue;
    }

    public void setToUserSplitPercentageValue(double toUserSplitPercentageValue) {
        this.toUserSplitPercentageValue = toUserSplitPercentageValue;
    }

    @Override
    public String toString() {
        return "Expense [id = " + id + ", firstName = " + fromUserName + ", lastName = " + toUserName + ", email = " + type ;
    }

}
