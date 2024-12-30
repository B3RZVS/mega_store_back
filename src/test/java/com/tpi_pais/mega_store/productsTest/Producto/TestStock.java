package com.tpi_pais.mega_store.productsTest.Producto;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.products.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestStock {
    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void U_U1PE_013() {
        // Scenario (I-U1PE-010)
        Integer stockMedio = 5;
        Integer stockMinimo = 10;

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            productoService.verificarStock(stockMedio, stockMinimo);
        });

        assert exception.getMessage().contains("El stock medio debe ser mayor que el stock mínimo.");
    }

    @Test
    void U_U1PE_014() {
        // Scenario (I-U1PE-011)
        Integer stockMedio = 0;
        Integer stockMinimo = 5;

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            productoService.verificarStock(stockMedio, stockMinimo);
        });

        assert exception.getMessage().contains("Stock Medio");
    }

    @Test
    void U_U1PE_015() {
        // Scenario (I-U1PE-012)
        Integer stockMedio = -1;
        Integer stockMinimo = 5;

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            productoService.verificarStock(stockMedio, stockMinimo);
        });

        assert exception.getMessage().contains("Stock Medio");
    }

    @Test
    void U_U1PE_016() {
        // Scenario (I-U1PE-013)
        Integer stockMedio = 10;
        Integer stockMinimo = 0;

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            productoService.verificarStock(stockMedio, stockMinimo);
        });

        assert exception.getMessage().contains("Stock Minimo");
    }

    @Test
    void U_U1PE_017() {
        // Scenario (I-U1PE-014)
        Integer stockMedio = 10;
        Integer stockMinimo = -1;

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            productoService.verificarStock(stockMedio, stockMinimo);
        });

        assert exception.getMessage().contains("Stock Minimo");
    }
}
