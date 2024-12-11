package com.tpi_pais.mega_store.productsTest.Producto;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.MessagesException;
import com.tpi_pais.mega_store.products.service.ProductoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

public class TestPrecio {

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void U_U1VL_001() {
        // Arrange
        BigDecimal precioCero = BigDecimal.valueOf(0);

        // Act & Assert
        BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> productoService.verificarPrecio(precioCero)
        );

        // Verifica el mensaje de la excepción
        Assertions.assertEquals(MessagesException.CAMPO_NUMERICO_MAYOR_0+"Precio", exception.getMessage());
    }

    @Test
    void U_U1VL_002() {
        // Arrange
        BigDecimal precioValido = BigDecimal.valueOf(0.01);

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> productoService.verificarPrecio(precioValido));
    }

    @Test
    void U_U1VL_003() {
        // Arrange
        BigDecimal precioNegativo = BigDecimal.valueOf(-1);

        // Act & Assert
        BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> productoService.verificarPrecio(precioNegativo)
        );

        // Verifica el mensaje de la excepción
        Assertions.assertEquals(MessagesException.CAMPO_NUMERICO_MAYOR_0+"Precio", exception.getMessage());
    }

}