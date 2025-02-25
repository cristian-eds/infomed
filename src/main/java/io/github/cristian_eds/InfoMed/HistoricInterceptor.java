package io.github.cristian_eds.InfoMed;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HistoricInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object hanldler, Exception exception) throws Exception{
        System.out.println(hanldler);
        System.out.println(request.getMethod());
        System.out.println(response.getStatus());
    }
}
