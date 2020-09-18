package com.getouo.frameworks.util;

import com.getouo.frameworks.ServiceException;
import io.seata.core.context.RootContext;
import io.seata.tm.api.GlobalTransaction;
import io.seata.tm.api.GlobalTransactionContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.getouo.frameworks.filter.ServletExceptionCaughtFilter.ERROR_RETHROW_PATH;
import static com.getouo.frameworks.filter.ServletExceptionCaughtFilter.EXCEPTION_ATTRIBUTE;


@RestController
//@RestControllerAdvice
@ControllerAdvice
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@Order(Ordered.LOWEST_PRECEDENCE)
public final class RethrowController {

    private static final String EX = "错误的访问或定向到: '" + ERROR_RETHROW_PATH + "'. 非异常请求:" +
            "attr[" + EXCEPTION_ATTRIBUTE + "] = ";

    private final Log logger = LogFactory.getLog(this.getClass());

    private void globalRollbackTry() {
        try {
            String xid = RootContext.getXID();
            if (xid != null) {
                GlobalTransaction reload = GlobalTransactionContext.reload(xid);
                reload.rollback();
            }
        } catch (Exception e) {
            logger.debug("rollbackTry error:", e);
        }
    }

    @ExceptionHandler(ServiceException.class)
    public com.getouo.msgtest.Message.Response invokeException(ServiceException exception, final HttpServletRequest request) {
        logger.info("catch ServiceException", exception);
        globalRollbackTry();
        return com.getouo.msgtest.Message.Response.newBuilder()
                .setPath(request.getServletPath()).setStatusCode(exception.statusCode).setReason(exception.reason).build();
    }

    @ExceptionHandler(Exception.class)
    public com.getouo.msgtest.Message.Response invokeException(Exception exception, final HttpServletRequest request, final HttpServletResponse response) {
        response.setStatus(555, "testerro");
//        String wrap = request.getHeader("wrap");
//        if (!StringUtils.isEmpty(wrap)) {
//
//        }

        logger.debug("un catch server error", exception);
        globalRollbackTry();
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
