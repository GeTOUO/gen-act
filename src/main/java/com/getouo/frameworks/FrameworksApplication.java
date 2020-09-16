package com.getouo.frameworks;

import com.getouo.frameworks.util.PackageScanner;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
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

@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class FrameworksApplication {

    @Bean
    public ProtobufJsonFormatHttpMessageConverter protobufJsonFormatHttpMessageConverter() {
        JsonFormat.TypeRegistry.Builder builder = JsonFormat.TypeRegistry.newBuilder();

        Set<Class<? extends Message>> classes = PackageScanner.loadClass("com.getouo.msgtest", Message.class);
        Set<Descriptors.Descriptor> descriptors = PackageScanner.loadProtoMessageDescriptors(classes);
        builder.add(descriptors);
        JsonFormat.TypeRegistry typeRegistry = builder.build();
        JsonFormat.Parser parser = JsonFormat.parser().usingTypeRegistry(typeRegistry);
        JsonFormat.Printer printer = JsonFormat.printer().usingTypeRegistry(typeRegistry)
                .includingDefaultValueFields();

        return new ProtobufJsonFormatHttpMessageConverter(parser, printer);
    }

    public static void main(String[] args) {
        SpringApplication.run(FrameworksApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RestController
    class EchoController {

        private final RestTemplate restTemplate;

        @Autowired
        public EchoController(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
        public com.getouo.msgtest.Message.Response echo(@PathVariable String str) {

            final String url = "http://exampleframework/echo2/" + str;
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add("Accept", "application/json");
//            RestTemplate template = new RestTemplate();
            HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
            ResponseEntity<com.getouo.msgtest.Message.Response> forObject = restTemplate.exchange(url, HttpMethod.GET, requestEntity, com.getouo.msgtest.Message.Response.class);
//            com.getouo.msgtest.Message.Response forObject = restTemplate.getForObject("http://exampleframework/echo2/" + str, com.getouo.msgtest.Message.Response.class);
            System.err.println("我被轻轻了" + forObject);

            return forObject.getBody();
//            return forObject;
        }

        @RequestMapping(value = "/echo2/{str}", method = RequestMethod.GET)
        public String echo2(@PathVariable String str) throws InvokeException {
            System.err.println("我被轻轻了2" + str);
            throw new InvokeException(123, "haha" + str);
//            return str + "resp";
        }
    }
}
