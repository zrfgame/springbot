package com.csdn.demo.common;

import java.sql.SQLException;

import javax.sql.DataSource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * 编写者:zrf
 * 创建时间:2019年8月12日
 * com.csdn.demo.common
 *DruidConfiguration.java
 */

@Configuration //注入
public class DruidConfiguration {
	private Logger logger = LoggerFactory.getLogger(DruidConfiguration.class);
	 
    @Value("${spring.datasource.druid.url}")
    private String dbUrl;
 
    @Value("${spring.datasource.druid.username}")
    private String username;
 
    @Value("${spring.datasource.druid.password}")
    private String password;
 
    @Value("${spring.datasource.druid.driver-class-name}")
    private String driverClassName;
 
    @Value("${spring.datasource.druid.initial-size}")
    private int initialSize;
 
    @Value("${spring.datasource.druid.min-idle}")
    private int minIdle;
 
    @Value("${spring.datasource.druid.max-active}")
    private int maxActive;
 
    @Value("${spring.datasource.druid.max-wait}")
    private int maxWait;
 
    @Value("${spring.datasource.druid.time-between-eviction-runs-millis}")
    private int timeBetweenEvictionRunsMillis;
 
    @Value("${spring.datasource.druid.min-evictable-idle-time-millis}")
    private int minEvictableIdleTimeMillis;
 
    @Value("${spring.datasource.druid.validation-query}")
    private String validationQuery;
 
    @Value("${spring.datasource.druid.test-while-idle}")
    private boolean testWhileIdle;
 
    @Value("${spring.datasource.druid.test-on-borrow}")
    private boolean testOnBorrow;
 
    @Value("${spring.datasource.druid.test-on-return}")
    private boolean testOnReturn;
 
    @Value("${spring.datasource.druid.pool-prepared-statements}")
    private boolean poolPreparedStatements;
 
    @Value("${spring.datasource.druid.max-pool-prepared-statement-per-connection-size}")
    private int maxPoolPreparedStatementPerConnectionSize;
 
    @Value("${spring.datasource.druid.filters}")
    private String filters;
 
    @Value("{spring.datasource.druid.connection-properties}")
    private String connectionProperties;
    
    @Bean     //声明其为Bean实例
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource(){
        DruidDataSource datasource = new DruidDataSource();
 
        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
 
        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        datasource.setConnectionProperties(connectionProperties);
 
        return datasource;
    }
    /**
     * 注册一个StatViewServlet
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public ServletRegistrationBean DruidStatViewServle(){
        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
    	//其实说白了就是注入Servlet，因为默认情况下Springboot是不提供Servlet的,参数为：StatViewServlet 类，就是我们自己定制的 Servlet 类 druid是路径下的Servlet类
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*"); 
        //添加初始化参数：initParams
        //白名单：
        servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny","192.168.242.1");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername","zrf");
        servletRegistrationBean.addInitParameter("loginPassword","123");
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable","true");
        return servletRegistrationBean;
    }
    
    /**
     * 注册一个：filterRegistrationBean
     * @return
     */
 
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public FilterRegistrationBean druidStatFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*");
        return filterRegistrationBean;
    }

}
