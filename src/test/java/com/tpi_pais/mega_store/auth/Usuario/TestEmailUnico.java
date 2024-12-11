package com.tpi_pais.mega_store.auth.Usuario;

import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.repository.UsuarioRepository;
import com.tpi_pais.mega_store.auth.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestEmailUnico {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioService = new UsuarioService(usuarioRepository, null, null, null, null, null);
    }

    @Test
    void I_U8PE_013() {
        // Scenario ()
        // Given
        String emailRegistrado = "usuario@example.com";
        when(usuarioRepository.findByEmail(emailRegistrado)).thenReturn(Optional.of(new Usuario()));

        // When
        boolean emailUtilizado = usuarioService.emailUtilizado(emailRegistrado);

        // Then
        assertTrue(emailUtilizado, "El sistema debería detectar que el email ya está registrado");
    }

    @Test
    void I_U8PE_014() {
        // Scenario (I-U8PE-014)
        // Given
        String emailNoRegistrado = "nuevo_usuario@example.com";
        when(usuarioRepository.findByEmail(emailNoRegistrado)).thenReturn(Optional.empty());

        // When
        boolean emailUtilizado = usuarioService.emailUtilizado(emailNoRegistrado);

        // Then
        assertFalse(emailUtilizado, "El sistema debería permitir registrar el email porque no está registrado");
    }
}
