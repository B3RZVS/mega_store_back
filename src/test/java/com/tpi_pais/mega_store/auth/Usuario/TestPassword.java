package com.tpi_pais.mega_store.auth.Usuario;

import static org.junit.jupiter.api.Assertions.*;

import com.tpi_pais.mega_store.auth.service.UsuarioService;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.MessagesException;
import com.tpi_pais.mega_store.utils.ExpresionesRegulares;
import com.tpi_pais.mega_store.utils.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

class TestPassword {

    private UsuarioService usuarioService;
    private ExpresionesRegulares expReg;
    private StringUtils stringUtils;

    // Tests para la longitud de la contraseña
    @BeforeEach
    public void setup() {
        expReg = new ExpresionesRegulares();
        stringUtils = new StringUtils();
        usuarioService = new UsuarioService(null,expReg,stringUtils,null,null,null);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void U_U10VL_027() {
        String password = "Short1";
        Exception exception = assertThrows(BadRequestException.class, () ->
                usuarioService.verificarPassword(password)
        );
        assertTrue(exception.getMessage().contains(MessagesException.FORMATO_INVALIDO + "Password"));
    }

    @Test
    void U_U10VL_028() {
        String password = "Passw0rd";
        assertThrows(BadRequestException.class, () ->
                usuarioService.verificarPassword(password)
        );
    }

    @Test
    void U_U10VL_029() {
        String password = "LongerPass1";
        Exception exception = assertThrows(BadRequestException.class, () ->
                usuarioService.verificarPassword(password)
        );
        assertTrue(exception.getMessage().contains("Formato incorrecto en el campo: Password"));
    }

    // Tests para la composición de la contraseña
    @Test
    void U_U10VL_030() {
        String password = "Password1";
        assertThrows(BadRequestException.class, () ->
                usuarioService.verificarPassword(password)
        );
    }

    @Test
    void U_U10VL_031() {
        String password = "password1";
        Exception exception = assertThrows(BadRequestException.class, () ->
                usuarioService.verificarPassword(password)
        );
        assertTrue(exception.getMessage().contains(MessagesException.FORMATO_INVALIDO + "Password"));
    }

    @Test
    void U_U10VL_032() {
        String password = "PASSWORD1";
        Exception exception = assertThrows(BadRequestException.class, () ->
                usuarioService.verificarPassword(password)
        );
        assertTrue(exception.getMessage().contains(MessagesException.FORMATO_INVALIDO + "Password"));
    }

    @Test
    void U_U10VL_033() {
        String password = "Password";
        Exception exception = assertThrows(BadRequestException.class, () ->
                usuarioService.verificarPassword(password)
        );
        assertTrue(exception.getMessage().contains(MessagesException.FORMATO_INVALIDO + "Password"));
    }

    @Test
    void validarPasswordConCaracterEspecial() {
        String password = "P@ssword1";
        assertDoesNotThrow(() ->
                usuarioService.verificarPassword(password)
        );
    }
}
