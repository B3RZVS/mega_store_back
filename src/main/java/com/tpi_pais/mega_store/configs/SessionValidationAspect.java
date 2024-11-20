package com.tpi_pais.mega_store.configs;

import com.tpi_pais.mega_store.auth.model.Sesion;
import com.tpi_pais.mega_store.auth.service.SesionService;
import com.tpi_pais.mega_store.exception.UnauthorizedException;
import com.tpi_pais.mega_store.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

@Aspect
@Component
public class SessionValidationAspect {

    @Autowired
    private HttpServletRequest request;



    @Autowired
    private SesionService sesionService;

    @Before("@annotation(SessionRequired)")
    public void validateSession(JoinPoint joinPoint) throws Exception {
        // Verificar si la sesión es válida usando tu clase de sesión y el token
        String token = request.getHeader("Authorization"); // Supongamos que el token viene en el encabezado Authorization

        if (!isValidToken(token)) {
            throw new UnauthorizedException("Sesión no válida o expirada");
        }
    }

    private boolean isValidToken(String token) {
        StringUtils stringUtils = new StringUtils();
        token = stringUtils.limpiarToken(token);
        Optional <Sesion> sesion = Optional.ofNullable(sesionService.obtenerSesionPorToken(token));
        return sesion.map(Sesion::esActivo).orElse(false);
    }
}