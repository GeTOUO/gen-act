package com.getouo.frameworks.cglibtest;

import com.getouo.frameworks.jooq.generator.tables.pojos.DictType;

import java.util.List;

public interface TestInterface {
    String getHalloWorld();

    List<DictType> getAllTypeOnly();
}