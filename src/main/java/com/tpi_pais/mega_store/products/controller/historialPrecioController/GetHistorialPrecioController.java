package com.tpi_pais.mega_store.products.controller.historialPrecioController;

import com.tpi_pais.mega_store.configs.SessionRequired;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.ColorDTO;
import com.tpi_pais.mega_store.products.dto.HistorialPrecioDTO;
import com.tpi_pais.mega_store.products.mapper.HistorialPrecioMapper;
import com.tpi_pais.mega_store.products.model.HistorialPrecio;
import com.tpi_pais.mega_store.products.service.IHistorialPrecioService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class GetHistorialPrecioController {

    private final IHistorialPrecioService modelService;

    private final ResponseService responseService;

    public GetHistorialPrecioController(IHistorialPrecioService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    @SessionRequired
    @GetMapping({"/historial-precio/{id}"})
    public ResponseEntity<ApiResponse<Object>>  getActual(@PathVariable Integer id) {
        return responseService.successResponse(HistorialPrecioMapper.toDTO(modelService.obtenerActual(id)), "OK");
    }

    @SessionRequired
    @GetMapping({"/historiales-precio/producto/{id}"})
    public ResponseEntity<ApiResponse<Object>>  getAllForProducto(@PathVariable Integer id) {
        return responseService.successResponse(modelService.listarPorProducto(id), "OK");
    }

    @SessionRequired
    @GetMapping({"/historiales-precio/usuario/{id}"})
    public ResponseEntity<ApiResponse<Object>>  getAllForUsuario(@PathVariable Integer id) {
        return responseService.successResponse(modelService.listarPorUsuario(id), "OK");
    }
}
