package com.mtf.kibs.config;

import com.mtf.kibs.interceptor.MyBatisEncryptInterceptor;
import org.apache.ibatis.plugin.Interceptor;            
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.mtf.kibs.mapper")
public class MySQLConfig {

    // 위에서 만든 인터셉터 주입
    @Autowired
    private MyBatisEncryptInterceptor myBatisEncryptInterceptor;

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));

        Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
        sessionFactory.setConfigLocation(myBatisConfig);

        // SqlSessionFactory에 플러그인(인터셉터) 등록
        sessionFactory.setPlugins(new Interceptor[]{myBatisEncryptInterceptor});

        return sessionFactory.getObject();
    }
}