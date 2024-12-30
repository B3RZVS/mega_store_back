package com.tpi_pais.mega_store.products.controller.ventaController;

import com.tpi_pais.mega_store.configs.SessionRequired;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.DetalleVentaDTO;
import com.tpi_pais.mega_store.products.dto.VentaDTO;
import com.tpi_pais.mega_store.products.service.IVentaService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/products") // Define la ruta base para los productos
public class PostVentaController {
    private final IVentaService modelService;
    private final ResponseService responseService;

    public PostVentaController(IVentaService modelService, ResponseService responseService) {
        this.responseService = responseService;
        this.modelService = modelService;
    }

    @SessionRequired
    @PostMapping("/venta")
    public ResponseEntity<ApiResponse<Object>> guardar(
            @RequestHeader("Authorization") String token,
            @RequestBody ArrayList<DetalleVentaDTO> detalles) {

        return responseService.successResponse(modelService.guardar(token, detalles), "Venta guardada");
    }
}
