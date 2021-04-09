package com.java.web.moviefinal2.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class WebUtils {

    public static String toString(User user) {
        StringBuilder sb = new StringBuilder();

        sb.append("UserName:").append(user.getUsername());

        Collection<GrantedAuthority> authorities = user.getAuthorities();
        if (authorities != null && !authorities.isEmpty()) {
            sb.append(" (");
            boolean first = true;
            for (GrantedAuthority a : authorities) {
                if (first) {
                    sb.append(a.getAuthority());
                    first = false;
                } else {
                    if(a.getAuthority().equals("ROLE_ADMIN")){
                        sb.append(", ").append("admin");
                    }
                    else {
                        sb.append(", ").append("user");
                    }
                }
            }
            sb.append(")");
        }
        return sb.toString();
    }

}