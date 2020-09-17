package com.getouo.frameworks.util;

import com.getouo.frameworks.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.getouo.frameworks.filter.ServletExceptionCaughtFilter.ERROR_RETHROW_PATH;
import static com.getouo.frameworks.filter.ServletExceptionCaughtFilter.EXCEPTION_ATTRIBUTE;


@RestController
@RestControllerAdvice
public final class RethrowController {

    private static final String EX = "错误的访问或定向到: '" + ERROR_RETHROW_PATH + "'. 非异常请求:" +
            "attr[" + EXCEPTION_ATTRIBUTE + "] = ";

    private final Log logger = LogFactory.getLog(this.getClass());

    @ExceptionHandler(ServiceException.class)
    public com.getouo.msgtest.Message.Response invokeException(ServiceException exception, final HttpServletRequest request) {
        logger.info("catch ServiceException", exception);
        return com.getouo.msgtest.Message.Response.newBuilder()
                .setPath(request.getServletPath()).setStatusCode(exception.statusCode).setReason(exception.reason).build();
    }

    @ExceptionHandler(Exception.class)
    public com.getouo.msgtest.Message.Response invokeException(Exception exception, final HttpServletRequest request) {
//        String wrap = request.getHeader("wrap");
//        if (!StringUtils.isEmpty(wrap)) {
//
//        }

        logger.info("un catch server error", exception);
        return com.getouo.msgtest.Message.Response.newBuilder()
                .setPath(request.getServletPath()).setStatusCode(500)
                .setReason("un catch server error:" + exception.getMessage() + " of " + exception.getClass()).build();
    }


    /**
     * 重新抛出异常
     */
    @RequestMapping(ERROR_RETHROW_PATH)
    public void rethrow(HttpServletRequest request) throws Throwable {
        Object exceptionState = request.getAttribute(EXCEPTION_ATTRIBUTE);
        if (exceptionState instanceof Throwable) throw (Throwable) exceptionState;
        else logger.warn(EX + exceptionState + "; [request] = " + request);
    }
}
