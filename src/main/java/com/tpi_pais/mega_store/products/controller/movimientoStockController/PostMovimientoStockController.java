package com.tpi_pais.mega_store.products.controller.movimientoStockController;

import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.MovimientoStockDTO;
import com.tpi_pais.mega_store.products.service.IMovimientoStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class PostMovimientoStockController {

    @Autowired
    private IMovimientoStockService movimientoStockService;

    @Autowired
    private ResponseService responseService;

    @PostMapping("/movimiento-stock")
    public ResponseEntity<?> guardar(@RequestBody MovimientoStockDTO model) {
        return responseService.successResponse(movimientoStockService.guardar(model), "Movimiento de stock guardado");
    }
}
