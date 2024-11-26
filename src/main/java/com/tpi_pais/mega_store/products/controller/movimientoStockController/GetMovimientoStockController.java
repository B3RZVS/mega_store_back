package com.tpi_pais.mega_store.products.controller.movimientoStockController;

import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.service.IMovimientoStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class GetMovimientoStockController {

    @Autowired
    private IMovimientoStockService movimientoStockService;

    @Autowired
    private ResponseService responseService;

    @GetMapping({"/movimientos-stock/{id}"})
    public ResponseEntity<?> getPorId(@PathVariable Integer id) {
        return responseService.successResponse(movimientoStockService.listarPorProducto(id), "OK");
    }

    @GetMapping({"/stock-actual/{id}"})
    public ResponseEntity<?> getStockActual(@PathVariable Integer id) {
        return responseService.successResponse(movimientoStockService.obtenerStockActual(id), "OK");
    }
}
