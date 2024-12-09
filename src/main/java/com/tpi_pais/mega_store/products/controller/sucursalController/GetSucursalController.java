package com.tpi_pais.mega_store.products.controller.sucursalController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.SucursalDTO;
import com.tpi_pais.mega_store.products.mapper.SucursalMapper;
import com.tpi_pais.mega_store.products.model.Sucursal;
import com.tpi_pais.mega_store.products.service.ISucursalService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gestionar las sucursales en la API.
 */
@RestController
@RequestMapping("/products")
public class GetSucursalController {

    private final ISucursalService modelService;  // Servicio para manejar la lógica de negocio de las sucursales.
    private final ResponseService responseService; // Servicio para manejar las respuestas estándar de la API.

    /**
     * Constructor que inyecta las dependencias necesarias para el controlador.
     */
    public GetSucursalController(ISucursalService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    /**
     * Endpoint para obtener todas las sucursales.
     *
     * @return ResponseEntity con las sucursales obtenidas o un mensaje de error si no hay ninguna.
     */
    @GetMapping("/sucursales")
    public ResponseEntity<ApiResponse<Object>> getAll() {
        List<SucursalDTO> sucursales = modelService.listar();
        if (sucursales.isEmpty()) {
            // Lanzamos una excepción si no hay sucursales, considerando usar una excepción 404
            throw new BadRequestException("No hay sucursales creadas");
        }
        return responseService.successResponse(sucursales, "OK");
    }

    /**
     * Endpoint para obtener una sucursal por su ID.
     *
     * @param id El ID de la sucursal a buscar.
     * @return ResponseEntity con la sucursal encontrada.
     */
    @GetMapping("/sucursal/{id}")
    public ResponseEntity<ApiResponse<Object>> getPorId(@PathVariable Integer id) {
        // Buscamos la sucursal por su ID.
        Sucursal model = modelService.buscarPorId(id);
        if (model == null) {
            // Si no se encuentra, podemos devolver un error 404
            throw new BadRequestException("Sucursal no encontrada");
        }

        // Convertimos el modelo a DTO
        SucursalDTO modelDTO = SucursalMapper.toDTO(model);
        return responseService.successResponse(modelDTO, "OK");
    }
}
