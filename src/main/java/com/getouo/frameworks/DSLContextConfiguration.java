package com.getouo.frameworks;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultExecuteListenerProvider;
//import org.springframework.boot.autoconfigure.jooq.SpringTransactionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

@Configuration
public class DSLContextConfiguration {

    @Bean
    public DSLContext dslContext(org.jooq.Configuration configuration) {
        return DSL.using(configuration);
    }

    @Bean
    public org.jooq.Configuration jooqConfiguration(DataSource druidDataSource) {
        TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy(druidDataSource);
        DataSourceTransactionManager txMgr = new DataSourceTransactionManager(druidDataSource);
        org.jooq.Configuration configuration = new DefaultConfiguration()
                .set(new DataSourceConnectionProvider(proxy))
                .set(new SpringTransactionProvider(txMgr)) //or import org.springframework.boot.autoconfigure.jooq.SpringTransactionProvider;
                .set(SQLDialect.MYSQL)
                .set(DefaultExecuteListenerProvider.providers(new SlowQueryListener()));//配置执行监听器;
        return configuration;
    }
}
