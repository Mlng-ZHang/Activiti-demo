package com.zm.demo.config;

import com.zm.demo.custom.interceptor.ActivitiCustomPostInterceptor;
import com.zm.demo.custom.interceptor.ActivitiCustomPreInterceptor;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Collections;

/**
 * @Name: ActivitiConfig
 * @Author: zhangming
 * @Date 2020/8/11 17:01
 * @Description:
 */
@Configuration
public class ActivitiConfig extends AbstractProcessEngineAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource activitiDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SpringProcessEngineConfiguration createConfig(){
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
//        configuration.setCustomPreCommandInterceptors(Collections.singletonList(new ActivitiCustomPreInterceptor()));
//        configuration.setCustomPostCommandInterceptors(Collections.singletonList(new ActivitiCustomPostInterceptor()));
        // 配置数据源和事务管理
        configuration.setDataSource(activitiDataSource());
        configuration.setTransactionManager(transactionManager());
        return configuration;
    }

    @Bean
    public ProcessEngineFactoryBean processEngine() {
        return super.springProcessEngineBean(createConfig());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(activitiDataSource());
    }
}
