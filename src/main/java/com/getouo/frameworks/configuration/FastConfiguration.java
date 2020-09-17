package com.getouo.frameworks.configuration;

import com.getouo.frameworks.util.PackageScanner;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Set;

@Configuration
public class FastConfiguration implements WebMvcConfigurer {

    private String messagePackage = "com.getouo.msgtest";

    private final RestTemplate rt = new RestTemplate();
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return rt;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, protobufJsonFormatHttpMessageConverter());
        rt.setMessageConverters(converters);
    }

    public ProtobufJsonFormatHttpMessageConverter protobufJsonFormatHttpMessageConverter() {
        JsonFormat.TypeRegistry.Builder builder = JsonFormat.TypeRegistry.newBuilder();
        Set<Class<? extends Message>> classes = PackageScanner.loadClass(messagePackage, Message.class);
        Set<Descriptors.Descriptor> descriptors = PackageScanner.loadProtoMessageDescriptors(classes);
        builder.add(descriptors);
        JsonFormat.TypeRegistry typeRegistry = builder.build();
        JsonFormat.Parser parser = JsonFormat.parser().usingTypeRegistry(typeRegistry);
        JsonFormat.Printer printer = JsonFormat.printer().usingTypeRegistry(typeRegistry)
                .includingDefaultValueFields();
        return new ProtobufJsonFormatHttpMessageConverter(parser, printer);
    }
}
