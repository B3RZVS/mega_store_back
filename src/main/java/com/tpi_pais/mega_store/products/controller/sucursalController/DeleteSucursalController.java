package com.tpi_pais.mega_store.products.controller.sucursalController;

import com.tpi_pais.mega_store.exception.NotFoundException;  // Excepción personalizada para el no encontrar el objeto
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.model.Sucursal;
import com.tpi_pais.mega_store.products.service.ISucursalService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para eliminar sucursales.
 */
@RestController
@RequestMapping("/products")
public class DeleteSucursalController {

    private final ISucursalService modelService;  // Servicio de negocio para las sucursales
    private final ResponseService responseService; // Servicio para manejar las respuestas estándar de la API

    /**
     * Constructor que inyecta las dependencias necesarias.
     */
    public DeleteSucursalController(ISucursalService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    /**
     * Endpoint para eliminar una sucursal por su ID.
     *
     * @param id El ID de la sucursal a eliminar.
     * @return ResponseEntity con el mensaje de éxito o error.
     */
    @DeleteMapping("/sucursal/{id}")
    public ResponseEntity<ApiResponse<Object>> eliminar(@PathVariable Integer id) {
        // Buscamos la sucursal por su ID.
        Sucursal model = modelService.buscarPorId(id);

        // Si no se encuentra la sucursal, lanzamos una excepción personalizada
        if (model == null) {
            throw new NotFoundException("Sucursal con el ID " + id + " no encontrada.");
        }

        // Eliminamos la sucursal
        modelService.eliminar(model);

        // Devolvemos una respuesta exitosa
        return responseService.successResponse(model, "Sucursal eliminada con éxito.");
    }
}
