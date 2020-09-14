package com.getouo.frameworks.controller;

import com.getouo.frameworks.ResponseForbiddenWrap;
import com.getouo.frameworks.ServiceOrController;
import com.getouo.frameworks.jooq.generator.tables.pojos.DictDetail;
import com.getouo.frameworks.jooq.generator.tables.pojos.DictType;
import com.getouo.msgtest.Message;
import com.google.protobuf.Any;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    private final ServiceOrController service;

    public TestController(ServiceOrController service) {
        this.service = service;
    }

    @RequestMapping("/addt")
    public void addDictType(DictType dt) {
        service.addDictType(dt);
    }

    @RequestMapping("/addd")
    public void addDictDetail(DictDetail detail) {
        service.addDictDetail(detail);
    }

    @RequestMapping("/delt")
    public void delDictType(String typeCode) {
        service.delDictType(typeCode);
    }
    @RequestMapping("/deltf")
    public void delDictTypeForce(String typeCode) {
        service.delDictTypeForce(typeCode);
    }

    @RequestMapping("/updatet")
    public void updateDictType(DictType dt) {
        service.updateDictType(dt);
    }

    @RequestMapping("/updated")
    public void updateDictDetail(DictDetail detail) {
        service.updateDictDetail(detail);
    }

    @RequestMapping("/gett")
    public Message.Response getTypeOnly(String typeCode) {
        DictType typeOnly = service.getTypeOnly(typeCode);
        Any pack = Any.pack(Message.ServiceStatus.newBuilder().setCode(100).setReason("200").build());
        Message.Response response = Message.Response.newBuilder().setReason("OK").setContent(pack).build();

        return response;
    }

//    @FixedResponseBody
    @RequestMapping("/gettt")
    @ResponseForbiddenWrap
    public Message.ServiceStatus getTypeOnly2(String typeCode) {
        return Message.ServiceStatus.newBuilder().setCode(100).setReason("200").build();
    }

    @RequestMapping("/gettd")
    public Map<DictType, List<DictDetail>> getTypeDetails(String typeCode) {
        return service.getTypeDetails(typeCode);
    }

    @RequestMapping("/getd")
    public void getDetail(String detailId) {
        service.getDetail(detailId);
    }

    @RequestMapping("/getts")
    public List<DictType> getAllTypeOnly() {
        return service.getAllTypeOnly();
    }

    @RequestMapping("/gettds")
    public Map<DictType, List<DictDetail>> getAllTypeDetails() {
        return service.getAllTypeDetails();
    }

    @Transactional
    @RequestMapping("/updateall")
    public void allUpdate(ServiceOrController.UpdateAll all) {
        service.allUpdate(all);
    }


}
