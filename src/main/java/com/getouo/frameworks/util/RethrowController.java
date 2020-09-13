package com.getouo.frameworks.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.getouo.frameworks.filter.ServletExceptionCaughtFilter.ERROR_RETHROW_PATH;
import static com.getouo.frameworks.filter.ServletExceptionCaughtFilter.EXCEPTION_ATTRIBUTE;

@RestController
public final class RethrowController {

    private static final String EX = "错误的访问或定向到: '" + ERROR_RETHROW_PATH + "'. 非异常请求:" +
            "attr[" + EXCEPTION_ATTRIBUTE + "] = ";

    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 重新抛出异常
     */
    @RequestMapping(ERROR_RETHROW_PATH)
    public void rethrow(HttpServletRequest request) throws Throwable {
        Object exceptionState = request.getAttribute(EXCEPTION_ATTRIBUTE);
        if (exceptionState != null && exceptionState instanceof Throwable) {
            throw (Throwable) exceptionState;
        } else {
            logger.warn(EX + exceptionState + "; [request] = " + request);
        }
    }
}
