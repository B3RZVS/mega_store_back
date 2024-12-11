package com.tpi_pais.mega_store.productsTest.Producto;


import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.MessagesException;
import com.tpi_pais.mega_store.products.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestImagen {
    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB en bytes

    @Test
    void U_U1PE_004() throws IOException {
        // Given
        MultipartFile imagen = mock(MultipartFile.class);
        when(imagen.getOriginalFilename()).thenReturn("imagen.jpg");
        when(imagen.getSize()).thenReturn(4 * 1024 * 1024L); // 4 MB
        when(imagen.isEmpty()).thenReturn(false);

        // When - Then
        assertDoesNotThrow(() -> productoService.verificarImagen(imagen));
    }

    @Test
    void U_U1PE_005() {
        // Given
        MultipartFile imagen = mock(MultipartFile.class);
        when(imagen.getOriginalFilename()).thenReturn("imagen.jpg");
        when(imagen.getSize()).thenReturn(6 * 1024 * 1024L); // 6 MB
        when(imagen.isEmpty()).thenReturn(false);

        // When
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> productoService.verificarImagen(imagen));

        // Then
        assertEquals("El archivo de imagen es demasiado grande.", exception.getMessage());
    }

    @Test
    void U_U1PE_006() {
        // Given
        MultipartFile imagen = mock(MultipartFile.class);
        when(imagen.getOriginalFilename()).thenReturn("imagen.gif"); // Formato no permitido
        when(imagen.getSize()).thenReturn(4 * 1024 * 1024L); // 4 MB
        when(imagen.isEmpty()).thenReturn(false);

        // When
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> productoService.verificarImagen(imagen));

        // Then
        assertEquals("Formato de archivo no permitido. Solo se permiten imágenes PNG y JPG.", exception.getMessage());
    }

    @Test
    void testRechazarImagenNula() {
        // Given
        MultipartFile imagen = null;

        // When
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> productoService.verificarImagen(imagen));

        // Then
        assertEquals(MessagesException.CAMPO_NO_ENVIADO + "Imagen", exception.getMessage());
    }

    @Test
    void testRechazarImagenVacia() {
        // Given
        MultipartFile imagen = mock(MultipartFile.class);
        when(imagen.isEmpty()).thenReturn(true);

        // When
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> productoService.verificarImagen(imagen));

        // Then
        assertEquals(MessagesException.CAMPO_NO_ENVIADO + "Imagen", exception.getMessage());
    }
}
