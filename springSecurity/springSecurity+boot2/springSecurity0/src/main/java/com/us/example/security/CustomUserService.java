package com.us.example.security;

import com.us.example.dao.UserDao;
import com.us.example.domain.SysRole;
import com.us.example.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CustomUserService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userDao.findByUserName(username);
        if(user == null){
           throw new UsernameNotFoundException("用户不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        for(SysRole sysRole : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(sysRole.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }
}
