package com.us.example.service;

import com.us.example.dao.PermissionDao;
import com.us.example.domain.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private PermissionDao permissionDao;

    private HashMap<String,Collection<ConfigAttribute>> map = null;

    public void loadResourceDefine(){
        map = new HashMap<String,Collection<ConfigAttribute>>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        List<Permission> permissions = permissionDao.findAll();
        for (Permission permission : permissions){
            array = new ArrayList<>();
            cfg= new SecurityConfig(permission.getName());
            // 此处只添加了权限的名字，其实还可以更多的权限信息，例如请求方法到ConfigAttribute的集合中
            array.add(cfg);
            // 用权限的getUrl()作为map的key，用configAttribute的集合作为value
            map.put(permission.getUrl(),array);
        }
    }

    // 此方法为了判定用户请求的url是否在权限列表中，如果在权限列表中则反悔decide方法，用来判定用户是否有此权限
    @Override// 这里同时可以将用户权限放到数据库或者缓存中间件中
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException{
        if(null == map) loadResourceDefine();
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for(Iterator<String> iter = map.keySet().iterator();iter.hasNext();){
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if(matcher.matches(request)){
                return map.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
