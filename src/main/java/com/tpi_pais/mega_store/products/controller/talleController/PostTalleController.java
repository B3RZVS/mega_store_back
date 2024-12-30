package com.tpi_pais.mega_store.products.controller.talleController;

// Importación de dependencias necesarias.
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.TalleDTO;
import com.tpi_pais.mega_store.products.model.Talle;
import com.tpi_pais.mega_store.products.service.ITalleService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Controlador REST para manejar operaciones POST relacionadas con "Talle".
@RestController
@RequestMapping("/products")
public class PostTalleController {

    private final ITalleService modelService;       // Servicio para lógica de negocio de "Talle".
    private final ResponseService responseService; // Servicio para generar respuestas estándar.

    // Constructor con inyección de dependencias.
    public PostTalleController(@Autowired ITalleService modelService, @Autowired ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    // Endpoint para guardar un nuevo talle.
    @PostMapping("/talle")
    public ResponseEntity<ApiResponse<Object>> guardar(@RequestBody TalleDTO model) {
        // Validar atributos del talle recibido.
        model = modelService.verificarAtributos(model);

        // Verificar si ya existe un talle con el mismo nombre.
        if (modelService.talleExistente(model.getNombre())) {
            // Si existe, verificar si está marcado como eliminado.
            Talle aux = modelService.buscarPorNombre(model.getNombre());
            if (aux.esEliminado()) {
                // Recuperar el talle eliminado y responder con un mensaje adecuado.
                modelService.recuperar(aux);
                return responseService.successResponse(model,
                        "Ya existía un objeto igual en la base de datos, objeto recuperado");
            } else {
                // Lanzar una excepción si el talle ya existe y no está eliminado.
                throw new BadRequestException("Ya existe un talle con ese nombre");
            }
        } else {
            // Si no existe, guardar el talle y responder con éxito.
            TalleDTO modelGuardado = modelService.guardar(model);
            return responseService.successResponse(modelGuardado, "Talle guardado");
        }
    }
}
