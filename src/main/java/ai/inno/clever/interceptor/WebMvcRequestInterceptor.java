package ai.inno.clever.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebMvcRequestInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory
        .getLogger(WebMvcRequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        logger.info("Request URL::" + request.getRequestURL().toString()
            + ":: Start Time=" + System.currentTimeMillis());
//        Enumeration<String> headerNames = request.getHeaderNames();
//
//        if (headerNames != null) {
//            while (headerNames.hasMoreElements()) {
//                logger.info("Header: " + request.getHeader(headerNames.nextElement()));
//            }
//        }
        logger.info(request.getHeader("X-Service-Token"));
        request.setAttribute("startTime", startTime);
        //if returned false, we need to make sure 'response' is sent
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("Request URL::" + request.getRequestURL().toString()
            + " Sent to Handler :: Current Time=" + System.currentTimeMillis());
        //we can add attributes in the modelAndView and use that in the view page
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        logger.info("Request URL::" + request.getRequestURL().toString()
            + ":: End Time=" + System.currentTimeMillis());
        logger.info("Request URL::" + request.getRequestURL().toString()
            + ":: Time Taken=" + (System.currentTimeMillis() - startTime));
    }

}
