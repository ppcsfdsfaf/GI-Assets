package com.example.taskplatform.config;

import com.example.taskplatform.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public AuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().startsWith("/api/auth/login")) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(401, "缺少认证信息");
            return false;
        }

        String token = authHeader.substring(7);
        return authService.getByToken(token)
                .map(user -> {
                    UserContext.set(user);
                    return true;
                })
                .orElseGet(() -> {
                    try {
                        response.sendError(401, "无效 token");
                    } catch (Exception ignored) {
                    }
                    return false;
                });
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear();
    }
}
