package com.getouo.frameworks.cglibtest;

import com.getouo.frameworks.jooq.generator.tables.pojos.DictType;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Cglib子类代理工厂
 * 对UserDao在内存中动态构建一个子类对象
 */
public class ProxyFactory implements MethodInterceptor {
    //维护目标对象
//    private Object target;

//    public ProxyFactory(Object target) {
//        this.target = target;
//    }

    //给目标对象创建一个代理对象
    public Object getProxyInstance(Object target){
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

        //执行目标对象的方法
        Object returnValue = null;
        try {
            returnValue = proxy.invokeSuper(obj, args);
        } catch (Throwable ignored){}
//        Object returnValue = method.invoke(obj, args);
//        Object returnValue = method.invoke(target, args);

        System.out.println("提交事务...");
        RestTemplate restTemplate = new RestTemplate();
        Object forObject = restTemplate.getForObject("http://localhost:8800/getts", method.getReturnType());

        return forObject;
//        return returnValue;
    }

    public static void main(String[] args) {
        //目标对象
        UserDao target = new UserDao();

        //代理对象
        ProxyFactory proxyFactory = new ProxyFactory();
        UserDao proxy = (UserDao) proxyFactory.getProxyInstance(target);

        //执行代理对象的方法
        proxy.save();

        TestInterface instance = (TestInterface) proxyFactory.getInstance(TestInterface.class);
        instance.getHalloWorld();

        List<DictType> allTypeOnly = instance.getAllTypeOnly();
        System.err.println(allTypeOnly.get(0));

    }
}