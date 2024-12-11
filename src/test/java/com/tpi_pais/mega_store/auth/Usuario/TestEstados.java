package com.tpi_pais.mega_store.auth.Usuario;

import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.repository.UsuarioRepository;
import com.tpi_pais.mega_store.auth.service.UsuarioService;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.MessagesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestEstados {

    @Mock
    private UsuarioRepository modelRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setFechaCreacion(LocalDateTime.now()); // Inicializa con fecha de creación
        usuario.setVerificado(false);
    }

    @Test
    void testEliminarUsuario_NoEliminado() {
        // Arrange
        usuario.setFechaEliminacion(null); // Usuario no eliminado inicialmente
        when(modelRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Act
        usuarioService.eliminar(usuario);

        // Assert
        assertTrue(usuario.esEliminado()); // Verificar que ahora está eliminado
        verify(modelRepository, times(1)).save(usuario); // Verificar que se guardó
    }

    @Test
    void testEliminarUsuario_YaEliminado() {
        // Arrange
        usuario.eliminar(); // Marca al usuario como eliminado

        // Act & Assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            usuarioService.eliminar(usuario);
        });

        assertEquals(MessagesException.OBJECTO_ELIMINADO, exception.getMessage()); // Verificar mensaje de error
        verify(modelRepository, never()).save(usuario); // Verificar que no se guardó
    }

    @Test
    void testRecuperarUsuario_Eliminado() {
        // Arrange
        usuario.eliminar(); // Marca al usuario como eliminado
        when(modelRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Act
        usuarioService.recuperar(usuario);

        // Assert
        assertFalse(usuario.esEliminado()); // Verificar que ahora no está eliminado
        verify(modelRepository, times(1)).save(usuario); // Verificar que se guardó
    }

    @Test
    void testRecuperarUsuario_NoEliminado() {
        // Arrange
        usuario.setFechaEliminacion(null); // Usuario no eliminado

        // Act & Assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            usuarioService.recuperar(usuario);
        });

        assertEquals(MessagesException.OBJECTO_NO_ELIMINADO, exception.getMessage()); // Verificar mensaje de error
        verify(modelRepository, never()).save(usuario); // Verificar que no se guardó
    }
}
