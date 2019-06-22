package cn.dante.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Aspect
@Component
public class WebExceptionAspect {

    private static final Logger logger = LoggerFactory.getLogger(WebExceptionAspect.class);

    //凡是注解了RequestMapping的方法都被拦截
//    yes
//    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)"
//      no
//     @Pointcut("execution(* org.springframework.web.bind.annotation.*(..))")
//    @Pointcut("execution(* org.springframework.jdbc.core..JdbcTemplate.query*(..))")
//    @Pointcut("@annotation(org.springframework.web.bind.annotation.*Mapping)")    //no

//    @Pointcut(Exception.class)    //n
//    @Pointcut("execution(* cn.dante.controller..*.*(..))")  //yes
//    @Pointcut("execution(* cn..*.controller..*.*(..))")  //yes
    //匹配cn及其子包下的所有controller
//    @Pointcut("execution(* cn..*.controller..*(..))")
//    @Pointcut("execution(* cn.dante.controller.*(..))")
    @Pointcut("execution(* cn.dante..*(..))")
    private void webPointcut() {
    }

    /**
     * 拦截web层异常，记录异常日志，并返回友好信息到前端 目前只拦截Exception，是否要拦截Error需再做考虑
     *
     * @param e
     *            异常对象
     */
    @AfterThrowing(pointcut = "webPointcut()", throwing = "e")
    public void handleThrowing(Exception e) {
        e.printStackTrace();
        logger.error("发现异常！" + e.getMessage());
//        JSon
        logger.error(JSON.toJSONString(e.getStackTrace()));
//        com.alibaba.fastjson.JSON.t
        //这里输入友好性信息
        writeContent("出现异常:"+JSON.toJSONString(e.getStackTrace()));
    }

    /**
     * 将内容输出到浏览器
     *
     * @param content
     *            输出内容
     */
    private void writeContent(String content) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain;charset=UTF-8");
        response.setHeader("icop-content-type", "exception");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.print(content);
        writer.flush();
        writer.close();
    }
}
