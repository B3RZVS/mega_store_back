package com.tpi_pais.mega_store.configs;

import com.tpi_pais.mega_store.auth.model.Sesion;
import com.tpi_pais.mega_store.auth.service.SesionService;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.UnauthorizedException;
import com.tpi_pais.mega_store.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

// Anotación que indica que esta clase es un Aspecto de AOP (Programación Orientada a Aspectos)
@Aspect
// Marca esta clase como un componente gestionado por Spring
@Component
public class SessionValidationAspect {

    // Dependencia para acceder a las solicitudes HTTP actuales
    private final HttpServletRequest request;

    // Servicio para interactuar con las sesiones
    private final SesionService sesionService;

    /**
     * Constructor para inyectar las dependencias requeridas.
     *
     * @param request       objeto para obtener información de la solicitud HTTP actual.
     * @param sesionService servicio que gestiona la lógica relacionada con las sesiones.
     */
    public SessionValidationAspect(HttpServletRequest request, SesionService sesionService) {
        this.request = request;
        this.sesionService = sesionService;
    }

    /**
     * Método que se ejecuta antes de cualquier método anotado con `@SessionRequired`.
     * Verifica si la sesión asociada al token de autorización es válida.
     *
     * @param joinPoint información sobre el punto de ejecución donde se aplica el aspecto.
     * @throws BadRequestException si el token no está presente o tiene un formato inválido.
     * @throws UnauthorizedException si la sesión es inválida o ha expirado.
     */
    @Before("@annotation(SessionRequired)") // Punto de corte para métodos con la anotación personalizada `@SessionRequired`
    public void validateSession(JoinPoint joinPoint) throws BadRequestException {
        // Obtiene el token de la cabecera "Authorization" de la solicitud HTTP
        String token = request.getHeader("Authorization");

        // Valida el token y lanza una excepción si no es válido
        if (!isValidToken(token)) {
            throw new UnauthorizedException("Sesión no válida o expirada");
        }
    }

    /**
     * Valida el token verificando si existe y si está activo.
     *
     * @param token el token enviado en la solicitud.
     * @return `true` si el token es válido, `false` de lo contrario.
     */
    private boolean isValidToken(String token) {
        // Limpia el token de caracteres no deseados utilizando `StringUtils`
        StringUtils stringUtils = new StringUtils();
        token = stringUtils.limpiarToken(token);

        // Busca la sesión asociada al token y verifica si está activa
        Optional<Sesion> sesion = Optional.ofNullable(sesionService.obtenerSesionPorToken(token));
        return sesion.map(Sesion::esActivo).orElse(false);
    }
}
