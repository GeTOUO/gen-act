//package db.migration;
//
//import org.flywaydb.core.api.migration.BaseJavaMigration;
//import org.flywaydb.core.api.migration.Context;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.SingleConnectionDataSource;
//
//import java.sql.ResultSet;
//import java.sql.Statement;
//
//public class V3__add_seata_table extends BaseJavaMigration {
//
//    private final String insertTableSql = "CREATE TABLE Person"
//            + "(id INTEGER not NULL, "
//            + " firstName VARCHAR(50), "
//            + " lastName VARCHAR(50), "
//            + " age INTEGER, "
//            + " PRIMARY KEY ( id ))";
//
//    public void migrate(Context context) throws Exception {
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(),
//                true));
//        jdbcTemplate.execute("alter table T_TEST_PIPELINE drop column type");
//        jdbcTemplate.execute("alter table T_PIPELINE alter state rename to edit_state");
//        jdbcTemplate.update("DROP table T_JOB");
//        jdbcTemplate.execute("create table if not exists t_job\n" +
//                "(\n" +
//                "    id                 int primary key AUTO_INCREMENT,\n" +
//                "    job_id             varchar(32) NOT NULL,\n" +
//                "    pipeline_id        int         NOT NULL,\n" +
//                "    state              varchar(16) NOT NULL,\n" +
//                "    fail_plugin_id     varchar(32),\n" +
//                "    fail_property_name varchar(32),\n" +
//                "    start_time         timestamp   NOT NULL COMMENT '启动时间',\n" +
//                "    finish_time        timestamp COMMENT '结束时间',\n" +
//                "    create_time        timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
//                "    update_time        timestamp   NOT NULL DEFAULT now() ON UPDATE now() COMMENT '最近修改时间',\n" +
//                "    publish_id         int         NOT NULL,\n" +
//                "    publish_version    int,\n" +
//                "    publish_committer varchar(32) NOT NULL ,\n" +
//                "    publish_message   varchar(100) NOT NULL ,\n" +
//                "    log                text\n" +
//                ")");
//    }
//
//}