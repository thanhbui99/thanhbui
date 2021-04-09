package com.java.web.moviefinal2.repository.custom.impl;

import com.java.web.moviefinal2.entity.AppUser;
import com.java.web.moviefinal2.repository.custom.IAppUserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AppUserRepositoryCustom implements IAppUserRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<AppUser> getStaff() {
        try {
            String sql = "select * from app_user u join user_role r on u.user_id = r.user_id where r.role_id = 3 and u.enabled = 1;";
            Query query = entityManager.createNativeQuery(sql, AppUser.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
