package com.us.example1.service;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

@Service
public class MyAccessDecisionManager implements AccessDecisionManager {

    // decide方法是判定是否拥有权限的方法，
    // authentication 是 customUserService中循环添加到GrantedAutority对象中的权限集合.
    // object 包含客户端发起的请求的request信息，可转换为HttpServletRequest request = (FilterInvocation)object

    // configAttibutes 为MyInvocatoinSecurityMetaDataSource的getAttibutes(Object object)这个方法反回的结果，
    // 此方法是为用户判定用户请求的url是否在权限列表中，如果在权限列表中，则返回给decide方法，用来判定用户是否由此权限。
    // 如果不再权限列表中则放行。
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        if(null == configAttributes || configAttributes.size() <= 0){
            return;
        }

        ConfigAttribute c;
        String needRole;
        for(Iterator<ConfigAttribute> iter=configAttributes.iterator();iter.hasNext();){
            c= iter.next();
            needRole = c.getAttribute();
            for(GrantedAuthority ga : authentication.getAuthorities()){
                if(needRole.trim().equals(ga.getAuthority())){
                    return;
                }
            }
        }
        throw new AccessDeniedException("no right");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
