package com.us.example1.dao;

import com.us.example1.domain.Permission;

import java.util.List;

public interface PermissionDao {
    public List<Permission> findAll();
    public List<Permission> findByAdminUserId(int userId);
}
