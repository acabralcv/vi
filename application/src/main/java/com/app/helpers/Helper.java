package com.app.helpers;

import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Helper {



    public static boolean isAdmin(Collection<? extends GrantedAuthority> authorities){

        if(authorities == null)
            return false;

        List<String> roles = new ArrayList<>();
        for (GrantedAuthority  a : authorities) {

            if(a.getAuthority().contains("admin"))
                return true;
        }

        return false;
    }

    public static boolean isGestor(Collection<? extends GrantedAuthority> authorities){

        if(authorities == null)
            return false;

        List<String> roles = new ArrayList<>();
        for (GrantedAuthority  a : authorities) {

            if(a.getAuthority().contains("user"))
                return true;
        }

        return false;
    }

}
