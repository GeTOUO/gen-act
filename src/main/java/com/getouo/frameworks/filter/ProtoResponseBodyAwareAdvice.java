package com.getouo.frameworks.filter;

import com.getouo.frameworks.ResponseForbiddenWrap;
import com.getouo.msgtest.Message;
import com.google.protobuf.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

@ControllerAdvice
public class ProtoResponseBodyAwareAdvice implements ResponseBodyAdvice<Object> {

    private final Map<Method, Boolean> supportsCache = new WeakHashMap<>();

    @Autowired
    HttpServletRequest request;
    private boolean check(MethodParameter methodParameter) {
        final Method method = methodParameter.getMethod();
        return supportsCache.computeIfAbsent(method, m -> {
            try {
                assert m != null;
                Class<?> returnType = m.getReturnType();
                System.err.println("suppo: " + returnType + "? " + request.getRequestURI());
                boolean fixed = methodParameter.hasMethodAnnotation(ResponseForbiddenWrap.class);
                return !fixed // 非限定返回类型
                        && methodParameter.getParameterIndex() == -1 // 是返回值描述
                        && com.google.protobuf.Message.class.isAssignableFrom(returnType) // proto 消息类
                        && !Message.Response.class.isAssignableFrom(returnType); // 并非返回包装类型
            } catch (Throwable tx) {
                return false;
            }
        });
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return check(methodParameter);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        System.err.println("thee : " + serverHttpRequest.getURI());
        List<String> wrap = serverHttpRequest.getHeaders().get("wrap");
        if (wrap != null && wrap.size() > 0) return o;
        if (o instanceof com.google.protobuf.Message && !(o instanceof Message.Response)) {
            Any hrl = Any.pack((com.google.protobuf.Message) o);
            return Message.Response.newBuilder().setPath(serverHttpRequest.getURI().getPath()).setContent(hrl).setReason("OK").build();
        }
        return o;
    }
}
