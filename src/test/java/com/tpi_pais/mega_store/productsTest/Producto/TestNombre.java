package com.tpi_pais.mega_store.productsTest.Producto;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.MessagesException;
import com.tpi_pais.mega_store.products.dto.ProductoDTO;
import com.tpi_pais.mega_store.products.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class TestNombre {

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void U_U1PE_007() {
        // Given
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombre("nombre");

        // When
        assertDoesNotThrow(() -> productoService.verificarNombre(productoDTO));

        // Then
        assertEquals("nombre", productoDTO.getNombre());
    }

    @Test
    void U_U1PE_008() {
        // Given
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombre("");

        // When
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> productoService.verificarNombre(productoDTO));

        // Then
        assertEquals(MessagesException.CAMPO_NO_ENVIADO + "Nombre", exception.getMessage());
    }

    @Test
    void testRechazarNombreNulo() {
        // Given
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombre(null);

        // When
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> productoService.verificarNombre(productoDTO));

        // Then
        assertEquals(MessagesException.CAMPO_NO_ENVIADO + "Nombre", exception.getMessage());
    }
}
