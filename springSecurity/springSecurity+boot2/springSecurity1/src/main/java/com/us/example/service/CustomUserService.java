package com.us.example.service;

import com.us.example.dao.PermissionDao;
import com.us.example.dao.UserDao;
import com.us.example.domain.Permission;
import com.us.example.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CustomUserService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Autowired
    PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userDao.findByUserName(username);

        if(null != user){
            List<Permission> permissions = permissionDao.findByAdminUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Permission permission : permissions){
                if(null != permission && null != permission.getName()){
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                    // 1.此处将当前用户的权限信息添加到GranteAuthority对象中，在后面进行权限验证时会使用GrantedAutority对象
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new User(user.getUsername(),user.getPassword(),grantedAuthorities);
        }else{
            throw new UsernameNotFoundException("user:"+username+"not exist!");
        }

    }
}
