package io.github.cristian_eds.InfoMed.security;

import io.github.cristian_eds.InfoMed.models.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/users").permitAll();
                    authorize.requestMatchers("/auth/**").permitAll();
                    authorize.requestMatchers(HttpMethod.PUT,"/medicine/item/{id}/status").authenticated();
                    authorize.requestMatchers(HttpMethod.POST,"/medicine/item/**").hasAnyRole(Role.ADMIN.toString(),Role.USER.toString());
                    authorize.requestMatchers(HttpMethod.DELETE,"/medicine/item/**").hasAnyRole(Role.ADMIN.toString(),Role.USER.toString());
                    authorize.requestMatchers(HttpMethod.POST,"/medicine/**").hasAnyRole(Role.ADMIN.toString(),Role.USER.toString());
                    authorize.requestMatchers(HttpMethod.PUT,"/medicine/**").hasAnyRole(Role.ADMIN.toString(),Role.USER.toString());
                    authorize.requestMatchers(HttpMethod.DELETE,"/medicine/**").hasAnyRole(Role.ADMIN.toString(),Role.USER.toString());
                    authorize.requestMatchers(HttpMethod.GET,"/person/**").authenticated();
                    authorize.requestMatchers("/person/**").hasRole(Role.ADMIN.toString());
                    authorize.requestMatchers("/image/**").hasAnyRole(Role.ADMIN.toString(),Role.USER.toString());
                    authorize.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
