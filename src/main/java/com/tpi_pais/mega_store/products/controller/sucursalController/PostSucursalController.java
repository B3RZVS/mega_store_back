package com.tpi_pais.mega_store.products.controller.sucursalController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import com.tpi_pais.mega_store.products.dto.SucursalDTO;
import com.tpi_pais.mega_store.products.model.Sucursal;
import com.tpi_pais.mega_store.products.service.ISucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para la creación de sucursales.
 */
@RestController
@RequestMapping("/products")
public class PostSucursalController {

    private final ISucursalService modelService;       // Servicio para manejar la lógica de negocio relacionada con las sucursales.
    private final ResponseService responseService;     // Servicio para generar respuestas estándar de la API.

    /**
     * Constructor que inyecta las dependencias necesarias.
     */
    public PostSucursalController(ISucursalService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    /**
     * Endpoint para guardar una nueva sucursal o recuperar una ya existente.
     *
     * @param model DTO que contiene los datos de la sucursal a guardar.
     * @return ResponseEntity con la respuesta y los datos correspondientes.
     */
    @PostMapping("/sucursal")
    public ResponseEntity<ApiResponse<Object>> guardar(@RequestBody SucursalDTO model) {
        // Verificación de atributos del modelo antes de proceder con la lógica.
        model = modelService.verificarAtributos(model);

        // Verificación si ya existe una sucursal con el mismo nombre.
        if (modelService.sucursalExistente(model.getNombre())) {
            // Si existe, se busca la sucursal por nombre.
            Sucursal aux = modelService.buscarPorNombre(model.getNombre());

            // Si la sucursal existe y ha sido eliminada, se recupera.
            if (aux.esEliminado()) {
                modelService.recuperar(aux);
                return responseService.successResponse(model, "Ya existía un objeto igual en la base de datos, objeto recuperado");
            } else {
                // Si la sucursal no está eliminada, se lanza una excepción.
                throw new BadRequestException("Ya existe una sucursal con ese nombre");
            }
        } else {
            // Si no existe, se guarda la nueva sucursal.
            SucursalDTO modelGuardado = modelService.guardar(model);
            return responseService.successResponse(modelGuardado, "Sucursal guardada");
        }
    }
}
