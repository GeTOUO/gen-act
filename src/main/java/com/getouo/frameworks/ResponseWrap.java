package com.getouo.frameworks;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseWrap {

    DataWraper.ResponseWraper value();
}
