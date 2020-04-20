/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.webdev.dao;

import static com.projects.webdev.dao.Dao.getSession;
import com.projects.webdev.exception.GroupException;
import com.projects.webdev.model.Groups;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author shubh
 */
@Repository
public class GroupDaoImpl extends Dao implements GroupsDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Groups save(Groups grp) throws GroupException {
        try {
            begin();
            getSession().save(grp);

            Hibernate.initialize(grp.getGrpExpenseList());
            Hibernate.initialize(grp.getCreatedBy());
            Hibernate.initialize(grp.getMemberEmail());
            Hibernate.initialize(grp.getMembers());
            commit();
            return grp;
        } catch (HibernateException ex) {
            rollback();
            throw new GroupException("Exception while creating the Group: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to create group!" + ex.getMessage());
        } finally {
            close();
        }

    }

    @Override
    public Groups get(String id) throws GroupException {
        try {
            System.out.println("3rd point");
            begin();
//            Groups grp = getSession().get(Groups.class, id);
            Query q = getSession().createQuery("FROM UserGroups WHERE groupId=:groupId");
            q.setLong("groupId", Long.parseLong(id));
            Groups grp = (Groups) q.uniqueResult();
            Hibernate.initialize(grp.getGrpExpenseList());
            Hibernate.initialize(grp.getCreatedBy());
            Hibernate.initialize(grp.getMemberEmail());
            Hibernate.initialize(grp.getMembers());
            grp.getGrpExpenseList().size();
            grp.getMemberEmail().size();
            grp.getMembers().size();
            commit();
//            System.out.println("4th point: " + grp.getGrpExpenseList() + " " + grp.getCreatedBy().toString() + " " + grp.getMembers());
            return grp;
        } catch (HibernateException ex) {
            rollback();
            throw new GroupException("Exception while viewing the Group: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to view group!" + ex.getMessage());
        } finally {
            close();
        }

    }

    @Override
    public List<Groups> list() throws GroupException {
        try {
            begin();
            List<Groups> groupList = getSession().createQuery("from UserGroups").list();
            commit();
            return groupList;
        } catch (HibernateException ex) {
            rollback();
            throw new GroupException("Exception while viewing all the Groups: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to view all groups!" + ex.getMessage());
        } finally {
            close();
        }

    }

    @Override
    public void update(String id, Groups grp) throws GroupException {
        try {
            begin();
            Session session = sessionFactory.getCurrentSession();
            System.out.println("point y3");
            
            Groups oldGroups = session.byId(Groups.class).load(Long.parseLong(id));
            System.out.println("point y4");
            oldGroups.setName(grp.getName());
            oldGroups.setCreatedBy(grp.getCreatedBy());
            oldGroups.setBalance(grp.getBalance());
            oldGroups.setGroupType(grp.getGroupType());
            System.out.println("point y5");
            session.flush();
            commit();
        } catch (HibernateException ex) {
            rollback();
            throw new GroupException("Exception while updating the Group: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to update the group!" + ex.getMessage());
        } finally {
            close();
        }

    }

    @Override
    public void delete(String id) throws GroupException {
        try {
            begin();
            Session session = sessionFactory.getCurrentSession();
            Groups grp = session.byId(Groups.class).load(Long.parseLong(id));
            session.delete(grp);
            commit();
        } catch (HibernateException ex) {
            rollback();
            throw new GroupException("Exception while deleting the Group: " + ex.getMessage());
        } catch (Exception ex) {
            rollback();
            throw new Error("Failed attempt to delete the group!" + ex.getMessage());
        } finally {
            close();
        }

    }

}
