package com.us.example1.dao;

import com.us.example1.domain.SysUser;


public interface UserDao {
    public SysUser findByUserName(String username);
}
