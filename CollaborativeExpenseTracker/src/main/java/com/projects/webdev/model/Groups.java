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
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author shubh
 */
@Entity(name = "UserGroups")
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//@GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private long groupId;
    private String name;
    private String groupType;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "createdBy", referencedColumnName = "userId")
    private User createdBy;
    
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name="USER_GROUPS", joinColumns={@JoinColumn(referencedColumnName="groupId")}
                                        , inverseJoinColumns={@JoinColumn(referencedColumnName="userId")})
    private Collection<User> members;
    
//    @OneToMany(mappedBy = "email", cascade = {CascadeType.MERGE})
    @JsonIgnore
    @ElementCollection
    private Set<String> memberEmail=new HashSet<>();
    
    private String memberEmailString;
    
    private double balance;

    @JsonIgnore
//    @ElementCollection
    @OneToMany(mappedBy = "sourceGroup", cascade = {CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<GroupsExpenses> grpExpenseList;

    public Groups() {

    }

    public Groups(long id, String name, String groupType, User createdBy, double balance, String memberEmailString) {
        this.groupId = id;
        this.name = name;
        this.groupType = groupType;
        this.createdBy = createdBy;
        this.balance = balance;
        this.members = new HashSet<User>();
        this.grpExpenseList = new HashSet<GroupsExpenses>();
        this.memberEmail = new HashSet<>();
        this.memberEmailString = memberEmailString;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public Collection<User> getMembers() {
        return members;
    }

    @JsonIgnore
    public void setMembers(Collection<User> members) {
        this.members = members;
    }

    public String getMemberEmailString() {
        return memberEmailString;
    }

    public void setMemberEmailString(String memberEmailString) {
        this.memberEmailString = memberEmailString;
    }

    public Collection<GroupsExpenses> getGrpExpenseList() {
        return grpExpenseList;
    }

    public void setGrpExpenseList(Collection<GroupsExpenses> grpExpenseList) {
        this.grpExpenseList = grpExpenseList;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public Set<String> getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(Set<String> memberEmail) {
        this.memberEmail = memberEmail;
    }
    
    public User getCreatedBy() {
        return createdBy;
    }

    @JsonIgnore
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
