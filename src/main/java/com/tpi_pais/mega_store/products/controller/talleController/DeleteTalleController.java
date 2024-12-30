package com.tpi_pais.mega_store.products.controller.talleController;

// Importación de dependencias necesarias.
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.model.Talle;
import com.tpi_pais.mega_store.products.service.ITalleService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Controlador REST para manejar la eliminación de objetos "Talle".
@RestController
@RequestMapping("/products")
public class DeleteTalleController {

    private final ITalleService modelService;       // Servicio para la lógica de negocio de "Talle".
    private final ResponseService responseService; // Servicio para generar respuestas estándar.

    // Constructor con inyección de dependencias.
    public DeleteTalleController(ITalleService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    /**
     * Endpoint para eliminar un talle por su ID.
     *
     * @param id ID del talle a eliminar.
     * @return ResponseEntity con el talle eliminado y un mensaje de éxito.
     */
    @DeleteMapping("/talle/{id}")
    public ResponseEntity<ApiResponse<Object>> eliminar(@PathVariable Integer id) {
        // Buscar el talle en la base de datos usando su ID.
        Talle model = modelService.buscarPorId(id);

        // Marcar el talle como eliminado.
        modelService.eliminar(model);

        // Responder con el objeto eliminado y un mensaje de confirmación.
        return responseService.successResponse(model, "Objeto eliminado");
    }
}
