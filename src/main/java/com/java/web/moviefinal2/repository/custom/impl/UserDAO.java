package com.java.web.moviefinal2.repository.custom.impl;

import com.java.web.moviefinal2.entity.AppUser;
import com.java.web.moviefinal2.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
@Transactional
public class UserDAO{

    @Autowired
    private EntityManager entityManager;


    public UserEntity findUserAccount(String userName) {
        try {
            String sql = "Select e from " + UserEntity.class.getName() + " e " //
                    + " Where e.userName = :userName  and e.enabled = 1 ";

            Query query = entityManager.createQuery(sql, UserEntity.class);
            query.setParameter("userName", userName);

            return (UserEntity) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}