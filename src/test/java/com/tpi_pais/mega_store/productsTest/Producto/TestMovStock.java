package com.tpi_pais.mega_store.productsTest.Producto;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.MessagesException;
import com.tpi_pais.mega_store.products.model.Producto;
import com.tpi_pais.mega_store.products.service.MovimientoStockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestMovStock {
    private MovimientoStockService movimientoStockService;

    @BeforeEach
    public void setup(){
        movimientoStockService = new MovimientoStockService(null, null, null, null, null, null);
    }

    @Test
    void U_U19PE_034() {
        Producto producto = new Producto();
        producto.setStockActual(20);
        assertDoesNotThrow(() ->
                movimientoStockService.verificarCantidad(10, true, producto)
        );
    }

    @Test
    void U_U19PE_035() {
        Producto producto = new Producto();
        producto.setStockActual(10);
        Exception exception = assertThrows(BadRequestException.class, () ->
                movimientoStockService.verificarCantidad(15, true, producto)
        );
        assertTrue(exception.getMessage().contains(MessagesException.STOCK_INSUFICIENTE));
    }

    @Test
    void U_U19PE_036() {
        Producto producto = new Producto();
        producto.setStockActual(10);
        Exception exception = assertThrows(BadRequestException.class, () ->
                movimientoStockService.verificarCantidad(5, null, producto)
        );
        assertTrue(exception.getMessage().contains("El campo esEgreso es obligatorio"));
    }
}
