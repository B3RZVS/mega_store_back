package com.tpi_pais.mega_store.products.controller.movimientoStockController;

import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.service.IMovimientoStockService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class GetMovimientoStockController {

    private final IMovimientoStockService movimientoStockService;

    private final ResponseService responseService;

    public GetMovimientoStockController(IMovimientoStockService movimientoStockService, ResponseService responseService) {
        this.movimientoStockService = movimientoStockService;
        this.responseService = responseService;
    }

    @GetMapping({"/movimientos-stock/{id}"})
    public ResponseEntity<ApiResponse<Object>>  getPorId(@PathVariable Integer id) {
        return responseService.successResponse(movimientoStockService.listarPorProducto(id), "OK");
    }

    @GetMapping({"/stock-actual/{id}"})
    public ResponseEntity<ApiResponse<Object>>  getStockActual(@PathVariable Integer id) {
        return responseService.successResponse(movimientoStockService.obtenerStockActual(id), "OK");
    }
}
