package com.tpi_pais.mega_store.auth.Sesion;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.tpi_pais.mega_store.auth.model.Sesion;
import com.tpi_pais.mega_store.auth.service.SesionService;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.UnauthorizedException;
import com.tpi_pais.mega_store.configs.SessionValidationAspect;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

class SessionValidationAspectTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private SesionService sesionService;

    private SessionValidationAspect sessionValidationAspect;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sessionValidationAspect = new SessionValidationAspect(request, sesionService);
    }

    @Test
    void I_U1PE_005() {
        // Given
        String tokenValido = "vdfkjlkjgdgdsht";
        Sesion sesionActiva = new Sesion();
        sesionActiva.setFechaCreacion(LocalDateTime.now());

        when(request.getHeader("Authorization")).thenReturn(tokenValido);
        when(sesionService.obtenerSesionPorToken(tokenValido)).thenReturn(sesionActiva);

        // When & Then
        assertDoesNotThrow(() -> sessionValidationAspect.validateSession(mock(JoinPoint.class)));
    }

    @Test
    void I_U1PE_006() {
        // Given
        String tokenExpirado = "hdfjdtjdjtdjtdj";
        Sesion sesionInactiva = new Sesion();
        sesionInactiva.setFechaCreacion(LocalDateTime.now().minusHours(9));

        when(request.getHeader("Authorization")).thenReturn(tokenExpirado);
        when(sesionService.obtenerSesionPorToken(tokenExpirado)).thenReturn(sesionInactiva);

        // When
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () ->
                sessionValidationAspect.validateSession(mock(JoinPoint.class))
        );

        // Then
        assertEquals("Sesión no válida o expirada", exception.getMessage());
    }

    @Test
    void I_U1PE_007() {
        // Given
        String tokenInexistente = "jsrstrstsrtstrs";

        when(request.getHeader("Authorization")).thenReturn(tokenInexistente);
        when(sesionService.obtenerSesionPorToken(tokenInexistente)).thenReturn(null);

        // When
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () ->
                sessionValidationAspect.validateSession(mock(JoinPoint.class))
        );

        // Then
        assertEquals("Sesión no válida o expirada", exception.getMessage());
    }

    @Test
    void I_U1PE_008() {
        // Given & When
        String uuid1 = Sesion.generarUUID();
        String uuid2 = Sesion.generarUUID();

        // Then
        assertNotNull(uuid1);
        assertNotNull(uuid2);
        assertNotEquals(uuid1, uuid2);
    }

    @Test
    void I_U1PE_009() {
        // Given
        Sesion sesion = new Sesion();
        sesion.setFechaCreacion(LocalDateTime.now().minusHours(5));

        // When
        boolean activa = sesion.esActivo();

        // Then
        assertTrue(activa);
    }

    @Test
    void I_U1PE_010() {
        // Given
        Sesion sesion = new Sesion();
        sesion.setFechaCreacion(LocalDateTime.now().minusHours(9));

        // When
        boolean activa = sesion.esActivo();

        // Then
        assertFalse(activa);
    }
}