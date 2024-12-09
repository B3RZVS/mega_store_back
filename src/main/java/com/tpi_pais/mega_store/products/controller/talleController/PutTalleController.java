package com.tpi_pais.mega_store.products.controller.talleController;

// Importación de dependencias necesarias.
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.TalleDTO;
import com.tpi_pais.mega_store.products.model.Talle;
import com.tpi_pais.mega_store.products.service.ITalleService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Controlador REST para operaciones relacionadas con "Talle".
@RestController
@RequestMapping("/products")
public class PutTalleController {

    private final ITalleService modelService;       // Servicio para lógica de negocio de "Talle".
    private final ResponseService responseService; // Servicio para generar respuestas estándar.

    // Constructor con inyección de dependencias.
    public PutTalleController(ITalleService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    // Actualiza un talle existente.
    @PutMapping("/talle")
    public ResponseEntity<ApiResponse<Object>> actualizar(@RequestBody TalleDTO model) {
        // Validar existencia y atributos del talle.
        Talle talleModificar = modelService.buscarPorId(model.getId());
        TalleDTO modelDTO = modelService.verificarAtributos(model);

        // Verificar si el nombre del talle ya existe.
        if (modelService.talleExistente(modelDTO.getNombre())) {
            throw new BadRequestException("Ya existe un Talle con ese nombre");
        }

        // Guardar los cambios y retornar la respuesta.
        TalleDTO modelGuardado = modelService.guardar(model);
        return responseService.successResponse(modelGuardado, "Talle actualizado");
    }

    // Recupera un talle eliminado lógicamente.
    @PutMapping("/talle/recuperar/{id}")
    public ResponseEntity<ApiResponse<Object>> recuperar(@PathVariable Integer id) {
        // Buscar el talle eliminado por su ID y restaurarlo.
        Talle model = modelService.buscarEliminadoPorId(id);
        modelService.recuperar(model);

        // Retornar respuesta exitosa.
        return responseService.successResponse(model, "Talle recuperado");
    }
}
