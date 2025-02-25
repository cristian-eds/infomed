package io.github.cristian_eds.InfoMed.controller.config;

import io.github.cristian_eds.InfoMed.HistoricInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final HistoricInterceptor historicInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(historicInterceptor)
                .addPathPatterns("/medicine/**");
    }
}
