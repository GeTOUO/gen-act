//package db.migration;
//
//import com.getouo.frameworks.util.PlatformUtil;
//import org.flywaydb.core.api.migration.BaseJavaMigration;
//import org.flywaydb.core.api.migration.Context;
//import org.flywaydb.core.internal.jdbc.JdbcTemplate;
//import org.jooq.tools.jdbc.SingleConnectionDataSource;
//
////import org.springframework.jdbc.core.JdbcTemplate;
////import org.springframework.jdbc.datasource.SingleConnectionDataSource;
//
//public class V0__create_seata_table extends BaseJavaMigration {
//
//
//    private final String CREATE_SEATA_TABLE_SQL = "" +
//            "CREATE TABLE if not exists `undo_log` (" + PlatformUtil.LINE_BREAK +
//            "  `id` bigint(20) NOT NULL AUTO_INCREMENT," + PlatformUtil.LINE_BREAK +
//            "  `branch_id` bigint(20) NOT NULL," + PlatformUtil.LINE_BREAK +
//            "  `xid` varchar(100) NOT NULL," + PlatformUtil.LINE_BREAK +
//            "  `context` varchar(128) NOT NULL," + PlatformUtil.LINE_BREAK +
//            "  `rollback_info` longblob NOT NULL," + PlatformUtil.LINE_BREAK +
//            "  `log_status` int(11) NOT NULL," + PlatformUtil.LINE_BREAK +
//            "  `log_created` datetime NOT NULL," + PlatformUtil.LINE_BREAK +
//            "  `log_modified` datetime NOT NULL," + PlatformUtil.LINE_BREAK +
//            "  PRIMARY KEY (`id`)," + PlatformUtil.LINE_BREAK +
//            "  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)" + PlatformUtil.LINE_BREAK +
//            ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8";
//
//    public void migrate(Context context) throws Exception {
////        JdbcTemplate jdbcTemplate = new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true));
//        SingleConnectionDataSource singleConnectionDataSource = new SingleConnectionDataSource(context.getConnection());
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(singleConnectionDataSource.getConnection());
////        jdbcTemplate.update("DROP table if exists undo_log");
//        jdbcTemplate.execute(CREATE_SEATA_TABLE_SQL);
//    }
//
//}