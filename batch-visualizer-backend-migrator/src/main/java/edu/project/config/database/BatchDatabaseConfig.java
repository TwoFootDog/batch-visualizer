package edu.project.config.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan(value = "edu/project/persistence/batchDB", sqlSessionFactoryRef = "batchDBSqlSessionFactory")
@EnableTransactionManagement
public class BatchDatabaseConfig {

/*
    @Value("${spring.datasource.batch-db.hikari.jdbc-url}")
    private String BATCH_DB_JDBC_URL;
    @Value("${spring.datasource.batch-db.hikari.username}")
    private String BATCH_DB_USERNAME;
    @Value("${spring.datasource.batch-db.hikari.password}")
    private String BATCH_DB_PASSWORD;
    @Value("${spring.datasource.batch-db.hikari.driver-class-name}")
    private String BATCH_DB_DRIVER_CLASS_NAME;
*/

    @Bean(name = "batchDBDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.batch-db.hikari")
    public DataSource batchDBDataSource() {
/*        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(BATCH_DB_JDBC_URL);
        config.setDriverClassName(BATCH_DB_DRIVER_CLASS_NAME);
        config.setUsername(BATCH_DB_USERNAME);
        cnfig.setPassword(BATCH_DB_PASSWORD);

        HikariDataSource datasource = new HikariDataSource(config);o
        return datasource;*/
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "batchDBSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("batchDBDataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:edu/project/persistence/batchDB/*.xml"));
        return sessionFactory.getObject();
    }

    @Bean(name = "batchDBSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlsessionTemplate(@Qualifier("batchDBSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        final SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }
}
