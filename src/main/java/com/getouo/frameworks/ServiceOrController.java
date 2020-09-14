package com.getouo.frameworks;

import com.getouo.frameworks.jooq.generator.tables.daos.DictDetailDao;
import com.getouo.frameworks.jooq.generator.tables.daos.DictTypeDao;
import com.getouo.frameworks.jooq.generator.tables.pojos.DictDetail;
import com.getouo.frameworks.jooq.generator.tables.pojos.DictType;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultTransactionProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

import static com.getouo.frameworks.jooq.generator.tables.DictType.DICT_TYPE;
import static com.getouo.frameworks.jooq.generator.tables.DictDetail.DICT_DETAIL;

// 便于测试， 跳过service
//@org.springframework.stereotype.Service
//@RestController
@Service
public class ServiceOrController {

    //    @Autowired
    final DSLContext dsl;
    final DictTypeDao typeDao;
    final DictDetailDao detailDao;
    final DataSource dataSource;

//    private DSLContext transactionSupport() {
//        ConnectionProvider connectionProvider =  new DataSourceConnectionProvider(dataSource);
//        TransactionProvider transactionProvider = new DefaultTransactionProvider(connectionProvider, false);
//        Configuration configuration = new DefaultConfiguration()
//                .set(connectionProvider)
//                .set(transactionProvider)
//                .set(SQLDialect.MYSQL);
//        return DSL.using(configuration);
//    }
//
//    public void withJOOQTransaction() {
//        transactionSupport().transaction()
//    }


    public ServiceOrController(DSLContext dsl, DictTypeDao typeDao, DictDetailDao detailDao, DataSource dataSource) {
        this.dsl = dsl;
        this.typeDao = typeDao;
        this.detailDao = detailDao;
        this.dataSource = dataSource;
    }

    @RequestMapping("/addt")
    public void addDictType(DictType dt) {
        typeDao.insert(dt);
    }

    @RequestMapping("/addd")
    public void addDictDetail(DictDetail detail) {
        detailDao.insert(detail);
    }

    @RequestMapping("/delt")
    public void delDictType(String typeCode) {
        typeDao.deleteById(typeCode);
    }
    @RequestMapping("/deltf")
    public void delDictTypeForce(String typeCode) {
        typeDao.deleteById(typeCode);
    }

    @RequestMapping("/updatet")
    public void updateDictType(DictType dt) {
        typeDao.update(dt);
    }

    @RequestMapping("/updated")
    public void updateDictDetail(DictDetail detail) {
        detailDao.update(detail);
    }

    @RequestMapping("/gett")
    public DictType getTypeOnly(String typeCode) {
        return typeDao.findById(typeCode);
    }

    @RequestMapping("/gettd")
    public Map<DictType, List<DictDetail>> getTypeDetails(String typeCode) {
        Map<DictType, List<DictDetail>> dictTypeListMap = this.dsl.select(DICT_TYPE.fields()).select(DICT_DETAIL.fields())
                .from(DICT_TYPE).leftJoin(DICT_DETAIL).on(DICT_TYPE.DICT_TYPE_CODE.eq(DICT_DETAIL.DICT_TYPE_ID)).fetchGroups(
                        r -> r.into(DICT_TYPE).into(DictType.class), //
                        r -> r.into(DICT_DETAIL).into(DictDetail.class) //
                );

        return dictTypeListMap;
    }

    @RequestMapping("/getd")
    public void getDetail(String detailId) {
        detailDao.findById(detailId);
    }

    @RequestMapping("/getts")
    public List<DictType> getAllTypeOnly() {
        return typeDao.findAll();
    }

    @RequestMapping("/gettds")
    public Map<DictType, List<DictDetail>> getAllTypeDetails() {
        Map<DictType, List<DictDetail>> dictTypeListMap = this.dsl.select(DICT_TYPE.fields()).select(DICT_DETAIL.fields())
                .from(DICT_TYPE).leftJoin(DICT_DETAIL).on(DICT_TYPE.DICT_TYPE_CODE.eq(DICT_DETAIL.DICT_TYPE_ID)).fetchGroups(
                        r -> r.into(DICT_TYPE).into(DictType.class), //
                        r -> r.into(DICT_DETAIL).into(DictDetail.class) //
                );

        return dictTypeListMap;
    }

    @Transactional
    @RequestMapping("/updateall")
    public void allUpdate(UpdateAll all) {
        System.err.println(all.t.toString());
        typeDao.update(all.t);
        detailDao.update(all.d);
    }

    public static class UpdateAll {
        DictType t;
        DictDetail d;
        public String h;

        public String getH() {
            return h;
        }

        public void setH(String h) {
            this.h = h;
        }

        public DictType getT() {
            return t;
        }

        public void setT(DictType t) {
            this.t = t;
        }

        public DictDetail getD() {
            return d;
        }

        public void setD(DictDetail d) {
            this.d = d;
        }
    }

}
