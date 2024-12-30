package com.tpi_pais.mega_store.products.controller.talleController;

// Importación de dependencias necesarias.
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.TalleDTO;
import com.tpi_pais.mega_store.products.mapper.TalleMapper;
import com.tpi_pais.mega_store.products.model.Talle;
import com.tpi_pais.mega_store.products.service.ITalleService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST para manejar operaciones GET relacionadas con "Talle".
@RestController
@RequestMapping("/products")
public class GetTalleController {

    private final ITalleService modelService;       // Servicio para lógica de negocio de "Talle".
    private final ResponseService responseService; // Servicio para generar respuestas estándar.

    // Constructor con inyección de dependencias.
    public GetTalleController(ITalleService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    // Endpoint para obtener todos los talles existentes.
    @GetMapping({"/talles"})
    public ResponseEntity<ApiResponse<Object>> getAll() {
        // Obtener la lista de todos los talles.
        List<TalleDTO> talles = modelService.listar();

        // Lanzar excepción si no hay talles creados.
        if (talles.isEmpty()) {
            throw new BadRequestException("No hay talles creados");
        }

        // Responder con la lista de talles y un mensaje de éxito.
        return responseService.successResponse(talles, "OK");
    }

    // Endpoint para obtener un talle específico por su ID.
    @GetMapping("/talle/{id}")
    public ResponseEntity<ApiResponse<Object>> getPorId(@PathVariable Integer id) {
        // Buscar el talle por su ID.
        Talle model = modelService.buscarPorId(id);

        // Convertir el modelo "Talle" a un DTO.
        TalleDTO modelDTO = TalleMapper.toDTO(model);

        // Responder con el DTO del talle y un mensaje de éxito.
        return responseService.successResponse(modelDTO, "OK");
    }
}
