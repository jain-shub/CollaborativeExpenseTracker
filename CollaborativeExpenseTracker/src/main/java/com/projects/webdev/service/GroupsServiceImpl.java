/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.webdev.dao.AuthDao;
import com.projects.webdev.dao.GroupsDao;
import com.projects.webdev.dao.UserDao;
import com.projects.webdev.exception.GroupException;
import com.projects.webdev.exception.UserException;
import com.projects.webdev.model.Groups;
import com.projects.webdev.model.User;
import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author shubh
 */
@Service
public class GroupsServiceImpl implements GroupsService {

    @Autowired
    private GroupsDao groupsDao;

    @Autowired
    AuthDao authDao;

    @Override
    @Transactional
    public Groups save(String userId, Groups group, HttpServletRequest request) throws GroupException, UserException {
        Groups newGroup = new Groups();
        Set<User> userSet = new HashSet<>();
        newGroup.setName(group.getName());
        newGroup.setGroupType(group.getGroupType());

        try {
            System.out.println("point aa1");
            String emailsVal = new ObjectMapper().readValue(group.getMemberEmailString(), String.class);
            System.out.println("point aa");
            System.out.println(emailsVal);
            String[] eVal = emailsVal.split(",");
            for (String ev : eVal) {
                System.out.println(ev);
                newGroup.getMemberEmail().add(ev);
            }
        } catch (IOException ex) {
            Logger.getLogger(GroupsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (group.getMemberEmail() != null) {
            System.out.println("point ab");
            for (String email : group.getMemberEmail()) {
                User us = authDao.getByEmail(email);
                System.out.println(us.toString());

                if (us == null) {
                    throw new UserException("User not found!");
                }
                userSet.add(us);
            }
            newGroup.setMemberEmail((Set<String>) group.getMemberEmail());
        } else {
            throw new Error("Member not found in group!");
        }
        User usr = authDao.getUser((String) request.getAttribute("userId"));
        newGroup.setMemberEmailString(group.getMemberEmailString() + "," + usr.getEmail());

        userSet.add(usr);
//        if (group.getMembers() != null) {
//            for (User member : group.getMembers()) {
//                if (member == null) {
//                    throw new Error("Member not found in group!");
//                } else {
//                    newGroup.getMembers().add(member);
//                }
//            }
//        }

        newGroup.setMembers(userSet);
        newGroup.setCreatedBy(usr);
        return groupsDao.save(newGroup);
    }

    @Override
    @Transactional
    public Groups get(String id, HttpServletRequest request) throws GroupException {
        System.out.println("2nd point");
        Groups group = groupsDao.get(id);
        request.setAttribute("currentGroup", group);
        return group;
    }

    @Override
    @Transactional
    public List<Groups> list(HttpServletRequest request) throws GroupException {
        List<Groups> newGrpList = groupsDao.list();
        List<Groups> resList = new ArrayList<>();
        try {
            User currentUsr = authDao.getUser((String) request.getAttribute("userId"));
            System.out.println("point p1");
            System.out.println(currentUsr.toString());
            System.out.println(currentUsr.getEmail());
            for (Groups groups : newGrpList) {
                System.out.println("point p2");
                System.out.println(groups.toString());
                System.out.println(groups.getCreatedBy().toString());
//                groups.getCreatedBy() == currentUsr || 
                if (groups.getMemberEmailString().contains(currentUsr.getEmail())) {
                    System.out.println(groups.getMemberEmailString());
                    System.out.println(groups.getMemberEmailString().contains(currentUsr.getEmail()));
                    resList.add(groups);
                }
            }
        } catch (UserException ex) {
            try {
                throw new UserException(ex.getMessage());
            } catch (UserException ex1) {
                Logger.getLogger(GroupsServiceImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return resList;
    }

    @Override
    @Transactional
    public void update(String id, Groups group, HttpServletRequest request) throws GroupException, UserException {
        User usr = authDao.getUser((String) request.getAttribute("userId"));
        System.out.println("point y2");
        System.out.println(group.getMemberEmailString());
        System.out.println(group.getGroupId());
        System.out.println(group.getName());
        if (!group.getMemberEmailString().contains(usr.getEmail())) {
             throw new UserException("Member not found!");
        }

        groupsDao.update(id, group);
    }

    @Override
    @Transactional
    public void delete(String id) throws GroupException {
        groupsDao.delete(id);
    }
}
