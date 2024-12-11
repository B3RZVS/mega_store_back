package com.tpi_pais.mega_store.auth.Usuario;

import com.tpi_pais.mega_store.auth.service.UsuarioService;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.MessagesException;
import com.tpi_pais.mega_store.utils.ExpresionesRegulares;
import com.tpi_pais.mega_store.utils.StringUtils;
import jakarta.mail.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestDireccion {

    private StringUtils stringUtils; // Clase que verifica largo, existencia, etc.

    @InjectMocks
    private UsuarioService usuarioService; // Servicio donde está la lógica a probar.

    @BeforeEach
    public void setup() {
        stringUtils = new StringUtils();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void U_U8VL_023() {
        // Arrange
        String input = "a".repeat(101); // Cadena de 101 caracteres.
        int min = 1;
        int max = 100;

        // Act & Assert
        BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> stringUtils.verificarLargo(input, min, max, "dirección")
        );

        // Verificar el mensaje de error.
        Assertions.assertEquals(
                "El campo dirección debe tener menos de " + max + " caracteres.",
                exception.getMessage()
        );
    }

    @Test
    void U_U8VL_022() {
        // Arrange
        String input = "a".repeat(100); // Cadena de 100 caracteres.
        int min = 1;
        int max = 100;

        // Act & Assert
        Assertions.assertDoesNotThrow(
                () -> stringUtils.verificarLargo(input, min, max, "dirección"),
                "El campo dirección de exactamente 100 caracteres debería ser válido."
        );
    }

    @Test
    void U_U8VL_024() {
        // Arrange
        String input = "a".repeat(99); // Cadena de 99 caracteres.
        int min = 1;
        int max = 100;

        // Act & Assert
        Assertions.assertDoesNotThrow(
                () -> stringUtils.verificarLargo(input, min, max, "dirección"),
                "El campo dirección de 99 caracteres debería ser válido."
        );
    }

}