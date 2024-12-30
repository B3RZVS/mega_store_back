package com.tpi_pais.mega_store.auth.Usuario;

import com.tpi_pais.mega_store.auth.repository.UsuarioRepository;
import com.tpi_pais.mega_store.auth.service.UsuarioService;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.MessagesException;
import com.tpi_pais.mega_store.utils.ExpresionesRegulares;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TestEmail {


    private ExpresionesRegulares expReg;

    @BeforeEach
    public void setup() {
        expReg = new ExpresionesRegulares();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testValidEmails() {
        // Arrange
        String[] validEmails = {
                "usuario@gmail.com",
                "usuario@hotmail.com",
                "usuario@yahoo.com",
                "usuario@frvm.utn.edu.ar",
                "usuario123@gmail.com"
        };

        // Act & Assert
        for (String email : validEmails) {
            boolean isValid = expReg.verificarEmail(email);
            Assertions.assertTrue(
                    isValid,
                    "El email " + email + " debería ser válido, pero no lo fue."
            );
        }
    }

    @Test
    void testInvalidEmails() {
        // Arrange
        String[] invalidEmails = {
                "usuario@",
                "usuario@gmail",
                "usuariogmail.com",
                "@gmail.com"
        };

        // Act & Assert
        for (String email : invalidEmails) {
            System.out.println(email);
            boolean isValid = expReg.verificarEmail(email);
            Assertions.assertFalse(
                    isValid,
                    "El email " + email + " no debería ser válido, pero fue marcado como válido."
            );
        }
    }

}
