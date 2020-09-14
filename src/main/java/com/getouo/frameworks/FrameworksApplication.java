package com.getouo.frameworks;

import com.getouo.frameworks.jooq.generator.*;
import com.getouo.frameworks.jooq.generator.tables.daos.DictDetailDao;
import com.getouo.frameworks.jooq.generator.tables.daos.DictTypeDao;
import com.getouo.frameworks.jooq.generator.tables.pojos.DictDetail;
import com.getouo.frameworks.jooq.generator.tables.pojos.DictType;
import com.getouo.frameworks.jooq.generator.tables.records.DictDetailRecord;
import com.getouo.frameworks.jooq.generator.tables.records.DictTypeRecord;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@SpringBootApplication
public class FrameworksApplication {

    @Autowired
    DSLContext dsl;

    @Autowired
    DictTypeDao dictTypeDao;
    @Autowired
    DictDetailDao dictDetailDao;

    com.getouo.frameworks.jooq.generator.tables.DictType TYPE_TABLE = com.getouo.frameworks.jooq.generator.tables.DictType.DICT_TYPE;
    com.getouo.frameworks.jooq.generator.tables.DictDetail DETAIL_TABLE = com.getouo.frameworks.jooq.generator.tables.DictDetail.DICT_DETAIL;

    /**
     * 新增
     */
    @RequestMapping("/add")
    public void add() {
        byte enabled = 1;

//        Table<DictTypeRecord> typeTable = dictTypeDao.getTable().as("dt");
//        Table<DictDetailRecord> detailTable = dictDetailDao.getTable().as("dd");
//        Schema schema = typeTable.getSchema();

        this.dsl
                .insertInto(this.TYPE_TABLE)
                .columns(this.TYPE_TABLE.DICT_TYPE_CODE, this.TYPE_TABLE.NAME, this.TYPE_TABLE.REMARK, this.TYPE_TABLE.CREATOR, this.TYPE_TABLE.EDITABLE)
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
        this.dsl.deleteFrom(TYPE_TABLE)
                .where(TYPE_TABLE.DICT_TYPE_CODE.eq(code))
                .execute();
    }

    /**
     * 更新
     */
    @RequestMapping("/update")
    public void update() {
        // 设置别名
        DictTypeRecord user = new DictTypeRecord();
        this.dsl.update(TYPE_TABLE)
                .set(TYPE_TABLE.REMARK, user.getRemark())
                .set(TYPE_TABLE.EDITABLE, user.getEditable())
                .set(TYPE_TABLE.CREATOR, user.getCreator())
                .where(TYPE_TABLE.DICT_TYPE_CODE.equal(user.getDictTypeCode()))
                .execute();
    }

    /**
     * 查询
     *
     * @return
     */
    @RequestMapping("/all")
    public List<DictType> getAll() {
        Result<DictTypeRecord> result = this.dsl
                .selectFrom(TYPE_TABLE)
                .orderBy(1)
                .fetch();
        List<DictType> into = result.into(DictType.class);
        return into;
    }

    @RequestMapping("/find")
    public Map<DictType, List<DictDetail>> find(String code) {

        Map<DictType, List<DictDetail>> dictTypeListMap = dictTypeDao.ctx().select(TYPE_TABLE.fields()).select(DETAIL_TABLE.fields())
                .from(TYPE_TABLE).leftJoin(DETAIL_TABLE).on(TYPE_TABLE.DICT_TYPE_CODE.eq(DETAIL_TABLE.DICT_TYPE_ID)).fetchGroups(
                        r -> r.into(TYPE_TABLE).into(DictType.class), //
                        r -> r.into(DETAIL_TABLE).into(DictDetail.class) //
                );

        return dictTypeListMap;
    }

    @Bean
    public ProtobufJsonFormatHttpMessageConverter protobufJsonFormatHttpMessageConverter() {
        return new ProtobufJsonFormatHttpMessageConverter();
    }
    public static void main(String[] args) {
        SpringApplication.run(FrameworksApplication.class, args);
    }
}
