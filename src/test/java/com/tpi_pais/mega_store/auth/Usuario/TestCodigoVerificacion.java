package com.tpi_pais.mega_store.auth.Usuario;

import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.repository.RolRepository;
import com.tpi_pais.mega_store.auth.repository.UsuarioRepository;
import com.tpi_pais.mega_store.auth.service.RolService;
import com.tpi_pais.mega_store.auth.service.UsuarioService;
import com.tpi_pais.mega_store.utils.ExpresionesRegulares;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

public class TestCodigoVerificacion {

    private ExpresionesRegulares expReg = new ExpresionesRegulares();

    @Mock
    private UsuarioRepository usuarioRepository; // Simula el repositorio.

    @InjectMocks
    private UsuarioService usuarioService; // Clase bajo prueba.

    @Mock
    private RolRepository rolRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.usuarioRepository = mock(UsuarioRepository.class);
        this.rolRepository = mock(RolRepository.class);
        this.usuarioService = new UsuarioService(usuarioRepository, expReg, null, new RolService(rolRepository), null, null);
    }

    @Test
    void testCrearUsuarioGeneraCodigoVerificacion() {
        // Arrange
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre("Juan");
        usuarioDTO.setEmail("juan@example.com");
        usuarioDTO.setTelefono("1234567890");
        usuarioDTO.setDireccionEnvio("Calle Falsa 123");
        usuarioDTO.setRolId(1);
        usuarioDTO.setPassword("password");

        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Usuario usuarioCreado = usuarioService.crearUsuario(usuarioDTO);

        // Assert
        assertNotNull(usuarioCreado.getCodigoVerificacion(), "El código de verificación no debería ser nulo.");
        assertEquals(6, usuarioCreado.getCodigoVerificacion().length(), "El código de verificación debe tener 6 caracteres.");
    }

}