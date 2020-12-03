package com.scc.log.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
@Aspect
@Component
public class LogRecordAOP {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogRecordAOP.class);

   /* @Autowired
    private LogRecordService logRecordService;*/

    @Autowired
    private HttpServletRequest request;

    @Pointcut("@annotation(com.scc.log.annotation.LogRecord)")
    public void LogAspect() {}

    @Around("LogAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        LOGGER.info("===========注解的方式start");
        //先查找类上的注解，没有再找方法上的注解
        LogRecord logRecord = joinPoint.getTarget().getClass().getAnnotation(LogRecord.class);
        if(logRecord == null){
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            logRecord = method.getAnnotation(LogRecord.class);
        }
        if (logRecord != null) {
            Object result =  joinPoint.proceed();
            LOGGER.info("===========注解的方式result="+result);
            UserSession userSession = (UserSession) request.getAttribute(Constants.HTTP_ATTR_SESSION_KEY);
            if (userSession != null) {
                OperaterLogVo operatorLog = new OperaterLogVo();
                if(result != null) {
                    operatorLog.setRemarks(result.toString());
                    //operatorLog.setLogContent(result.getLogContent());
                } else {
                    //系统中导出功能等返回结果为非Result的接口
                    //默认都为成功
                    operatorLog.setRemarks("OK");
                }
                operatorLog.setSystemName(logRecord.system());
                operatorLog.setModuleName(logRecord.module());
                operatorLog.setMenuLv1(logRecord.menuLv1());
                operatorLog.setMenuLv2(logRecord.menuLv2());

                String requestURI = request.getRequestURI().substring(request.getContextPath().length());
                operatorLog.setUrl(request.getProtocol().split("/")[0] + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + requestURI);

                operatorLog.setUserCode(String.valueOf(userSession.getUserId()));
                operatorLog.setUserName(userSession.getUserName());
                operatorLog.setIp(IPUtils.getIpAddress(request));
                try {
                    LOGGER.info("注解的方式："+operatorLog);
                    //logRecordService.insertOperatorLog(operatorLog);
                } catch (Exception e) {
                    LOGGER.error("日志记录出错："+ e.getMessage());
                }
            }
            return result;
        } else {
            return joinPoint.proceed();
        }
    }
}
