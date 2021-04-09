package com.java.web.moviefinal2.repository;

import com.java.web.moviefinal2.entity.AppUser;
import com.java.web.moviefinal2.repository.custom.IAppUserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser,Long> {
    AppUser findByEmail(String email);
    AppUser findByUserName(String name);
    AppUser findByEncrytedPassword(String pass);
    AppUser findOneByUserNameAndEnabled(String name, int enabled);
    AppUser findOneByUserName(String name);
}
