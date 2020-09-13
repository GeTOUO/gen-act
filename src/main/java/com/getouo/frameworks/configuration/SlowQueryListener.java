package com.getouo.frameworks.configuration;

import org.jooq.ExecuteContext;
import org.jooq.ExecuteType;
import org.jooq.Query;
import org.jooq.impl.DefaultExecuteListener;
import org.jooq.tools.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SlowQueryListener extends DefaultExecuteListener {
    private Logger logger = LoggerFactory.getLogger(SlowQueryListener.class);
    StopWatch watch;

    @Override
    public void executeStart(ExecuteContext ctx) {
        super.executeStart(ctx);
        watch = new StopWatch();
    }

    @Override
    public void executeEnd(ExecuteContext ctx) {
        try {
            super.executeEnd(ctx);
//            if (watch.split() > 1_000_000_000L) {//记录执行时间超过1s的操作
            if (watch.split() > 1_000_000_000L / 1000) {//记录执行时间超过1ms的操作
                ExecuteType type = ctx.type();
                StringBuffer sqlBuffer = new StringBuffer();
                if (type == ExecuteType.BATCH) {
                    for (Query query : ctx.batchQueries()) {
                        sqlBuffer.append(query.toString()).append("\n");
                    }
                } else {
                    sqlBuffer.append(ctx.query() == null ? "blank query " : ctx.query().toString());
                }
                watch.splitInfo(String.format("Slow SQL query meta executed : [ %s ]",
                        sqlBuffer.toString()));
            }
        } catch (Exception e) {
            logger.error(" SlowQueryListener has occur,fix bug  ", e);
        }
    }
}