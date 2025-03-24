package io.github.cristian_eds.InfoMed.interceptors;

import io.github.cristian_eds.InfoMed.controller.dto.HistoricLogSaveDTO;
import io.github.cristian_eds.InfoMed.models.HistoricLog;
import io.github.cristian_eds.InfoMed.models.User;
import io.github.cristian_eds.InfoMed.models.enums.ActionType;
import io.github.cristian_eds.InfoMed.service.HistoricLogService;
import io.github.cristian_eds.InfoMed.service.SecurityService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class HistoricInterceptor implements HandlerInterceptor {

    private final HistoricLogService historicLogService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception{

        if (handler instanceof HandlerMethod handlerMethod && !request.getMethod().equals("GET")) {

            ActionType actionType = ActionType.valueOf(request.getMethod());
            String description = handlerMethod.getMethod().getName();
            Integer status = response.getStatus();

            HistoricLogSaveDTO historicLogSaveDTO =
                   new HistoricLogSaveDTO(actionType,description,status);

            historicLogService.save(historicLogSaveDTO);
        }

    }
}
