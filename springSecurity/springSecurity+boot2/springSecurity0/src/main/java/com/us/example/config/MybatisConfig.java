package com.us.example.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan
public class MybatisConfig {

    @Autowired
    private DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
   public SqlSessionFactoryBean sqlSessionFactory(ApplicationContext applicationContext) throws Exception{
       SqlSessionFactoryBean seqSessionFactoryBean = new SqlSessionFactoryBean();
        seqSessionFactoryBean.setDataSource(dataSource);
        seqSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath*:mapper/*.xml"));
       return seqSessionFactoryBean;
   }

}
