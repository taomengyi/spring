package com.us.example1.config;

import com.us.example1.service.CustomUserService;
import com.us.example1.service.MyFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

     @Autowired
     private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

     @Bean
     UserDetailsService customUserService(){// 注册userDetailService 的 bean
         return  new CustomUserService();
     }

     @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
         auth.userDetailsService(customUserService()); // userDetail service 验证
     }

     @Override
    protected void configure(HttpSecurity http) throws Exception{
         http.authorizeRequests()
                 .antMatchers("/static/css/**").permitAll()
                 .anyRequest().authenticated()
                 .and()
                 .formLogin()
                 .loginPage("/login")
                 .defaultSuccessUrl("/")
                 .failureUrl("/login?erro")
                 .permitAll()// 登录页面任意用户访问
                 .and()
                 .logout().permitAll();// 注销行为任意用户访问
         http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
     }


}
