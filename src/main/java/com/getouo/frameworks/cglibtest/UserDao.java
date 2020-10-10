package com.getouo.frameworks.cglibtest;

/**
 * 目标对象,没有实现任何接口
 */
public class UserDao {

    public void save() {
        System.err.println("----savesavesavesavesavesavesavesavesavesave!----");
        hello();
    }

    public String hello() {
        System.err.println("----hellohellohellohellohellohellohellohellohellohello!----");
        return "hellohellohellohellohellohellohellohellohellohello";
    }
}