package com.us.example.dao;

import com.us.example.domain.Permission;

import java.util.List;

public interface PermissionDao {
    public List<Permission> findAll();
    public List<Permission> findByAdminUserId(int userId);
}
