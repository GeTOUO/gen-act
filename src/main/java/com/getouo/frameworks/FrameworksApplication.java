package com.getouo.frameworks;

import com.swnote.jooq.generator.tables.pojos.TestDictType;
import com.swnote.jooq.generator.tables.records.TestDictTypeRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@SpringBootApplication
public class FrameworksApplication {

    @Autowired
    DSLContext dsl;

    com.swnote.jooq.generator.tables.TestDictType rows = com.swnote.jooq.generator.tables.TestDictType.TEST_DICT_TYPE;

    /**
     * 新增
     */
    @RequestMapping("/add")
    public void add() {
        byte enabled = 1;
        this.dsl
                .insertInto(rows)
                .columns(rows.DICT_TYPE_CODE, rows.NAME, rows.REMARK, rows.CREATOR, rows.EDITABLE)
                .values("code1", "小毛", "10086", "123456", enabled)
                .execute();
    }

    /**
     * 删除
     *
     * @param code
     */
    @RequestMapping("/del")
    public void deleteUserById(String code) {
        this.dsl.deleteFrom(rows)
                .where(rows.DICT_TYPE_CODE.eq(code))
                .execute();
    }

    /**
     * 更新
     */
    @RequestMapping("/update")
    public void update() {
        // 设置别名
        TestDictTypeRecord user = new TestDictTypeRecord();
        this.dsl.update(rows)
                .set(rows.REMARK, user.getRemark())
                .set(rows.EDITABLE, user.getEditable())
                .set(rows.CREATOR, user.getCreator())
                .where(rows.DICT_TYPE_CODE.equal(user.getDictTypeCode()))
                .execute();
    }

    /**
     * 查询
     *
     * @return
     */
    @RequestMapping("/all")
    public List<TestDictType> getAll() {
        Result<TestDictTypeRecord> result = this.dsl
                .selectFrom(rows)
                .orderBy(1)
                .fetch();
        List<TestDictType> into = result.into(TestDictType.class);
        return into;
    }

    public Result<TestDictTypeRecord> find() {
        Result<TestDictTypeRecord> fetch = dsl.selectFrom(rows).fetch();
        return fetch;
    }

    public static void main(String[] args) {
        SpringApplication.run(FrameworksApplication.class, args);
    }
}
