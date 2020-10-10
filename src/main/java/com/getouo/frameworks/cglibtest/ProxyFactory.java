package com.getouo.frameworks.cglibtest;

import com.getouo.frameworks.jooq.generator.tables.pojos.DictType;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cglib子类代理工厂
 * 对UserDao在内存中动态构建一个子类对象
 */
public class ProxyFactory implements MethodInterceptor {
    //维护目标对象
    private Object target;

//    public ProxyFactory(Object target) {
//        this.target = target;
//    }

    //给目标对象创建一个代理对象
    public Object getProxyInstance(Object target) {
        this.target = target;
        //1.工具类
        Enhancer en = new Enhancer();
        //2.设置父类
        en.setSuperclass(target.getClass());
        //3.设置回调函数
        en.setCallback(this);
        //4.创建子类(代理对象)
        return en.create();

    }

    public Object getInstance(Class claxx) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(claxx);
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("开始事务...");
        UserDao ud = (UserDao) obj;

        System.out.println("method..." + method.getName());

        System.out.println("method..." + method.getReturnType());

        //执行目标对象的方法
        Object returnValue = null;
        try {
//            if (method.getName().equals("save")) {
//                returnValue = proxy.invokeSuper(obj, args);
//            } else {
//                System.out.println("noproxy...");
////                returnValue = proxy.invokeSuper(obj, args);
//            }
                returnValue = proxy.invoke(this.target, args);

        } catch (Throwable ignored) {
        }
//        Object returnValue = method.invoke(obj, args);
//        Object returnValue = method.invoke(target, args);

        System.out.println("提交事务...");
        return 100;
//        return forObject;
//        return returnValue;
    }

    public static void main(String[] args) {
        //目标对象
        UserDao target = new UserDao();

        ResponseEntity<Map<String, Object>> s = new ResponseEntity<>(new HashMap<>(), HttpStatus.ACCEPTED);
        ResponseEntity<ConcurrentHashMap<String, Object>> s2 = new ResponseEntity<>(new ConcurrentHashMap<>(), HttpStatus.ACCEPTED);
        System.err.println(ResponseEntity.class.isAssignableFrom(s.getClass()));
        //代理对象
        ProxyFactory proxyFactory = new ProxyFactory();
        UserDao proxy = (UserDao) proxyFactory.getProxyInstance(target);

        //执行代理对象的方法
        proxy.save();
        String hello = proxy.hello();
//        System.err.println(hello);

//        TestInterface instance = (TestInterface) proxyFactory.getInstance(TestInterface.class);
//        instance.getHalloWorld();

//        List<DictType> allTypeOnly = instance.getAllTypeOnly();
//        System.err.println(allTypeOnly.get(0));

    }
}