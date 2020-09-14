package com.getouo.frameworks.filter;

import com.getouo.frameworks.ResponseForbiddenWrap;
import com.getouo.msgtest.Message;
import com.google.protobuf.Any;
import com.google.protobuf.MessageLite;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ProtoResponseBodyAwareAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
//        methodParameter.getMethod()
        Class<?> returnBodyType = methodParameter.getParameterType();
        boolean fixed = methodParameter.hasMethodAnnotation(ResponseForbiddenWrap.class);
        System.err.println("has fixed" + fixed);
        return !fixed && methodParameter.getParameterIndex() == -1
                && MessageLite.class.isAssignableFrom(returnBodyType)
                && !Message.Response.class.isAssignableFrom(returnBodyType);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof MessageLite && !(o instanceof Message.Response)) {
            Any hrl = Any.pack(Message.ServiceStatus.newBuilder().setCode(123).setReason("hrl").build());
            return Message.Response.newBuilder().setContent(hrl).setReason("OK").build();
        }
        return o;
    }
}
