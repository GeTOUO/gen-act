package com.getouo.frameworks;

import com.getouo.frameworks.api.IController;
import com.getouo.frameworks.util.PackageScanner;
import com.getouo.msgtest.Message;
import com.google.protobuf.Descriptors;
import com.google.protobuf.util.JsonFormat;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@EnableFeignClients
@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class FrameworksApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrameworksApplication.class, args);
    }



    @RestController
    class EchoController {

        private final RestTemplate restTemplate;

        @Autowired
        public EchoController(RestTemplate restTemplate) {
            restTemplate.getMessageConverters().forEach(System.err::println);
            System.err.println("rtrestTemplate:" + restTemplate);
            this.restTemplate = restTemplate;
        }
        @Autowired
        IController iController;

        @RequestMapping(value = "/voids", method = RequestMethod.GET)
        public Object voids() {
            return iController.getAllTypeOnly();
        }

        @GlobalTransactional
        @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
        public com.getouo.msgtest.Message.User echo(@PathVariable String str) {

            final String url = "http://exampleframework/echo2/" + str;
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add("Accept", "application/json");
            requestHeaders.add("wrap", "en");
//            RestTemplate template = new RestTemplate();
            HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
//            ResponseEntity<com.getouo.msgtest.Message.Response> forObject = restTemplate.exchange(url, HttpMethod.GET, requestEntity, com.getouo.msgtest.Message.Response.class);
            System.err.println("Accept");
            try {

                ResponseEntity<com.getouo.msgtest.Message.User> forObject =
                        restTemplate.exchange(url, HttpMethod.GET, requestEntity, com.getouo.msgtest.Message.User.class);
    //            com.getouo.msgtest.Message.Response forObject = restTemplate.getForObject("http://exampleframework/echo2/" + str, com.getouo.msgtest.Message.Response.class);
                System.err.println("-----了" + forObject);

                return forObject.getBody();
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
//            return forObject;
        }

        @RequestMapping(value = "/echo2/{str}", method = RequestMethod.GET)
        public Message.User echo2(@PathVariable String str) throws ServiceException {
            System.err.println("我被轻轻了2" + str);
            Message.User build = Message.User.newBuilder().setId(1).setUsername("uname").setAddr("ip").build();
//            return build;
            throw new ServiceException(123, "haha" + str);
//            return str + "resp";
        }
    }
}
