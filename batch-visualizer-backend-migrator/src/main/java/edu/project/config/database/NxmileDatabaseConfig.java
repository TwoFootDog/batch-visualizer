package edu.project.config.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@MapperScan(value = "edu/project/persistence/nxmileDB", sqlSessionFactoryRef = "nxmileDBSqlSessionFactory")
@EnableTransactionManagement
public class NxmileDatabaseConfig {

/*    @Value("${spring.datasource.nxmile-db.hikari.jdbc-url}")
    private String NXMILE_DB_JDBC_URL;
    @Value("${spring.datasource.nxmile-db.hikari.username}")
    private String NXMILE_DB_USERNAME;
    @Value("${spring.datasource.nxmile-db.hikari.password}")
    private String NXMILE_DB_PASSWORD;
    @Value("${spring.datasource.nxmile-db.hikari.driver-class-name}")
    private String NXMILE_DB_DRIVER_CLASS_NAME;*/

    @Bean(name = "nxmileDBDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.nxmile-db.hikari")
    public DataSource nxmileDBDataSource() {
/*        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(NXMILE_DB_JDBC_URL);
        config.setDriverClassName(NXMILE_DB_DRIVER_CLASS_NAME);
        config.setUsername(NXMILE_DB_USERNAME);
        config.setPassword(NXMILE_DB_PASSWORD);

        HikariDataSource datasource = new HikariDataSource(config);
        return datasource;*/
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "nxmileDBSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("nxmileDBDataSource") DataSource dataSource) throws Exception{
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:edu/project/persistence/nxmileDB/*.xml"));
        return sessionFactory.getObject();

    }
    @Bean(name = "nxmileDBSqlSessionTemplate")
    public SqlSessionTemplate sqlsessionTemplate(@Qualifier("nxmileDBSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        final SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }


}
