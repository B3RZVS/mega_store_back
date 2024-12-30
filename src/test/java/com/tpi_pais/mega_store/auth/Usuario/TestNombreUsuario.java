package com.tpi_pais.mega_store.auth.Usuario;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.utils.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestNombreUsuario {
    private StringUtils stringUtils;


    @BeforeEach
    public void setup() {
        stringUtils = new StringUtils();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validarNombreUsuarioNoVacio() {
        // Arrange
        String nombreUsuario = "Abril";

        // Act & Assert
        Assertions.assertDoesNotThrow(
                () -> stringUtils.verificarExistencia(nombreUsuario, "nombre"),
                "El sistema no debería lanzar excepción cuando el campo nombre tiene un valor válido."
        );
    }

    @Test
    void U_U10VL_025() {
        // Arrange
        String nombreUsuario = "";

        // Act & Assert
        BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> stringUtils.verificarExistencia(nombreUsuario, "nombre")
        );

        // Verificar el mensaje de error.
        Assertions.assertEquals(
                "El campo nombre es requerido.",
                exception.getMessage(),
                "El mensaje de error para un nombre vacío no coincide con lo esperado."
        );
    }

    @Test
    void U_U10VL_026() {
        // Arrange
        String nombreUsuario = null;

        // Act & Assert
        BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> stringUtils.verificarExistencia(nombreUsuario, "nombre")
        );

        // Verificar el mensaje de error.
        Assertions.assertEquals(
                "El campo nombre es requerido.",
                exception.getMessage(),
                "El mensaje de error para un nombre nulo no coincide con lo esperado."
        );
    }
}
