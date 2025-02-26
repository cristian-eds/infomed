package io.github.cristian_eds.InfoMed.interceptors;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
public class HistoricInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception{
        System.out.println(handler);
        System.out.println(request.getMethod());
        System.out.println(response.getStatus());
        System.out.println( request.getUserPrincipal());

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            System.out.println("Tipo do Handler: " + handlerMethod.getBeanType().getName());
            System.out.println("Método do Handler: " + handlerMethod.getMethod().getName());
            System.out.println("Anotações do Handler: " + Arrays.toString(handlerMethod.getMethod().getAnnotations()));
        }

    }
}
