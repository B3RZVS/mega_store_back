package com.tpi_pais.mega_store.products.controller.movimientoStockController;

import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.MovimientoStockDTO;
import com.tpi_pais.mega_store.products.dto.MovimientosDTO;
import com.tpi_pais.mega_store.products.service.IMovimientoStockService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class PostMovimientoStockController {

    private final IMovimientoStockService movimientoStockService;

    private final ResponseService responseService;

    public PostMovimientoStockController(IMovimientoStockService movimientoStockService, ResponseService responseService) {
        this.movimientoStockService = movimientoStockService;
        this.responseService = responseService;
    }

    @PostMapping("/movimiento-stock")
    public ResponseEntity<ApiResponse<Object>>  guardar(@RequestBody MovimientosDTO modelDTO) {
        return responseService.successResponse(movimientoStockService.guardar(modelDTO), "Movimiento de stock guardado");
    }
}
