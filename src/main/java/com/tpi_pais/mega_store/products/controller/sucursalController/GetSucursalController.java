package com.tpi_pais.mega_store.products.controller.sucursalController;

import com.tpi_pais.mega_store.products.dto.SucursalDTO;
import com.tpi_pais.mega_store.products.mapper.SucursalMapper;
import com.tpi_pais.mega_store.products.model.Sucursal;
import com.tpi_pais.mega_store.products.service.ISucursalService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
@RequestMapping("/products")
public class GetSucursalController {
    @Autowired
    private ISucursalService modelService;
    @GetMapping({"/sucursales"})
    public ResponseEntity<?> getAll() {
        ApiResponse<Object> response = new ApiResponse<>(
                200,
                "OK",
                modelService.listar(),
                null
        );
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/sucursal/{id}")
    public ResponseEntity<?> getPorId(@PathVariable Integer id){
        /*
         * Validaciones:
         * 1) Que el id se haya enviado.
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 2) Que el id sea un entero.
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 3) Que exista una Sucursal con dicho id.
         *   Se realiza la busqueda del obj y si el mismo retorna null se devuelve el badrequest
         * 4) Que la Sucursal encontrada no este eliminada.
         *   Si se encuentra la Sucursal, y la misma esta elimianda se retorna un badrequest.
         * En caso de que pase todas las verificacioens devuelve el recurso encontrado.
         * */

        try {
            Sucursal model = modelService.buscarPorId(id);

            if (model == null) {
                ApiResponse<Object> response = new ApiResponse<>(
                        404,
                        "Error: Not Found",
                        null,
                        "No se encontró la categoría con el ID."
                );
                return ResponseEntity.badRequest().body(response);
            }

            if (model.esEliminado()) {
                ApiResponse<Object> response = new ApiResponse<>(
                        400,
                        "Error: Bad Request.",
                        null,
                        "No se puede traer un objeto que este eliminado."
                );
                return ResponseEntity.badRequest().body(response);
            }

            SucursalDTO modelDTO = SucursalMapper.toDTO(model);
            ApiResponse<Object> response = new ApiResponse<>(
                    200,
                    "OK",
                    modelDTO,
                    null
            );
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            ApiResponse<Object> response = new ApiResponse<>(
                    400,
                    "Error: Error inesperado.",
                    null,
                    ""+e
            );
            return ResponseEntity.badRequest().body(response);
        }

    }
    // Manejador de excepciones para cuando el parámetro no es del tipo esperado (ej. no es un entero)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        // Creamos una respuesta en formato JSON con el error
        String error = String.format("El parámetro '%s' debe ser un número entero válido.", ex.getName());
        ApiResponse<Object> response = new ApiResponse<>(
                200,
                "Error de tipo de argumento",
                null,
                error
        );

        return ResponseEntity.badRequest().body(response);
    }
    
}
