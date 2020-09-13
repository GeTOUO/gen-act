package com.getouo.frameworks.filter;

import javax.servlet.Filter;

public interface ServletExceptionCaughtFilter extends Filter {
    String EXCEPTION_ATTRIBUTE = "REQUEST_EXCEPTION_ATTRIBUTE";
    String ERROR_RETHROW_PATH = "/error/rethrow";
}
