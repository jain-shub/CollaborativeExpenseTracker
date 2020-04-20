/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.model;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author shubh
 */
@Embeddable
@Entity(name="GroupsExpenses")
public class GroupsExpenses {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private long id;
    private String name;
    
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinTable(name="GROUP_GROUPEXPENSES", joinColumns=@JoinColumn(referencedColumnName="id")
                                        , inverseJoinColumns=@JoinColumn(referencedColumnName="groupId"))
    private Groups sourceGroup;
    
    private double amount;
    private double dividePercentage;
    
    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name="paidBy", referencedColumnName="userId")
    private User paidBy;
    
    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name="createdBy", referencedColumnName="userId")
    private User createdBy;

    public GroupsExpenses() {
    }

    public GroupsExpenses(long id, String name, Groups sourceGroup, double amount, double dividePercentage, User paidBy, User createdBy) {
        this.id = id;
        this.name = name;
        this.sourceGroup = sourceGroup;
        this.amount = amount;
        this.dividePercentage = dividePercentage;
        this.paidBy = paidBy;
        this.createdBy = createdBy;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getDividePercentage() {
        return dividePercentage;
    }

    public void setDividePercentage(double dividePercentage) {
        this.dividePercentage = dividePercentage;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(User paidBy) {
        this.paidBy = paidBy;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Groups getSourceGroup() {
        return sourceGroup;
    }

    public void setSourceGroup(Groups sourceGroup) {
        this.sourceGroup = sourceGroup;
    }
    
}
