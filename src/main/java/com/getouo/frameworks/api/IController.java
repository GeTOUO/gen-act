package com.getouo.frameworks.api;

import com.getouo.frameworks.ServiceOrController;
import com.getouo.frameworks.jooq.generator.tables.pojos.DictDetail;
import com.getouo.frameworks.jooq.generator.tables.pojos.DictType;
import com.getouo.msgtest.Message;
import com.google.protobuf.Any;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

//@RequestMapping("/tests")
public interface IController {

    @FeignClient("exampleframework")
    interface ApiSupport extends IController {}

    interface AAA {
        @RequestMapping("/adda2")
        public void startTrans() throws Exception;
    }
    @FeignClient("exampleframework2")
    interface ApiSupport2 extends AAA {}

//    @Transactional
    @GlobalTransactional
    @RequestMapping("/adda")
    public void startTrans() throws Exception;


//    @GlobalTransactional
    @RequestMapping("/addt")
    public void addDictType(DictType dt) throws Exception;


//    @GlobalTransactional
    @RequestMapping("/addd")
    public void addDictDetail(DictDetail detail);

    @RequestMapping("/delt")
    public void delDictType(String typeCode);

    @RequestMapping("/deltf")
    public void delDictTypeForce(String typeCode);

    @RequestMapping("/updatet")
    public void updateDictType(DictType dt);

    @RequestMapping("/updated")
    public void updateDictDetail(DictDetail detail);

    @RequestMapping("/gett")
    public Message.Response getTypeOnly(String typeCode);

//    @FixedResponseBody
    @RequestMapping("/gettt")
//    @ResponseForbiddenWrap
    public Message.ServiceStatus getTypeOnly2(String typeCode);

    @RequestMapping("/gettd")
    public Map<DictType, List<DictDetail>> getTypeDetails(String typeCode);

    @RequestMapping("/getd")
    public void getDetail(String detailId);

    @RequestMapping("/getts")
    public List<DictType> getAllTypeOnly();

    @RequestMapping("/gettds")
    public Map<DictType, List<DictDetail>> getAllTypeDetails();

    @RequestMapping("/updateall")
    public void allUpdate(ServiceOrController.UpdateAll all);


}
