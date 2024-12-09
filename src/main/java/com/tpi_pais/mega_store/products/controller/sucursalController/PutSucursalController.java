package com.tpi_pais.mega_store.products.controller.sucursalController;

// Importación de dependencias necesarias.
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.SucursalDTO;
import com.tpi_pais.mega_store.products.model.Sucursal;
import com.tpi_pais.mega_store.products.service.ISucursalService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Controlador REST para manejar las operaciones PUT relacionadas con "Sucursal".
@RestController
@RequestMapping("/products")
public class PutSucursalController {

    private final ISucursalService modelService;       // Servicio para manejar la lógica de negocio de Sucursal.
    private final ResponseService responseService;     // Servicio para generar respuestas estándar.

    // Constructor con inyección de dependencias.
    public PutSucursalController(ISucursalService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    /**
     * Endpoint para actualizar una sucursal existente.
     *
     * @param model DTO que contiene los datos de la sucursal a actualizar.
     * @return ResponseEntity con los datos actualizados de la sucursal y un mensaje de éxito.
     */
    @PutMapping("/sucursal")
    public ResponseEntity<ApiResponse<Object>> actualizar(@RequestBody SucursalDTO model) {
        // Buscar la sucursal por su ID.
        Sucursal sucursalModificar = modelService.buscarPorId(model.getId());

        // Verificar y validar los atributos del DTO proporcionado.
        SucursalDTO modelDTO = modelService.verificarAtributos(model);

        // Verificar si ya existe una sucursal con el mismo nombre.
        if (modelService.sucursalExistente(modelDTO.getNombre())) {
            throw new BadRequestException("Ya existe una Sucursal con ese nombre");
        } else {
            // Guardar la sucursal actualizada y devolver la respuesta.
            SucursalDTO modelGuardado = modelService.guardar(model);
            return responseService.successResponse(modelGuardado, "Sucursal actualizada");
        }
    }

    /**
     * Endpoint para recuperar una sucursal que fue eliminada.
     *
     * @param id ID de la sucursal a recuperar.
     * @return ResponseEntity con los datos de la sucursal recuperada y un mensaje de éxito.
     */
    @PutMapping("/sucursal/recuperar/{id}")
    public ResponseEntity<ApiResponse<Object>> recuperar(@PathVariable Integer id) {
        // Buscar la sucursal eliminada por su ID.
        Sucursal model = modelService.buscarEliminadoPorId(id);

        // Recuperar la sucursal.
        modelService.recuperar(model);

        // Devolver la respuesta con los datos recuperados.
        return responseService.successResponse(model, "Sucursal recuperada");
    }
}
