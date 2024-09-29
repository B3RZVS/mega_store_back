package com.tpi_pais.mega_store.products.controller.categoriaController;
import com.tpi_pais.mega_store.products.model.Categoria;
import com.tpi_pais.mega_store.products.service.ICategoriaService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/products")
public class DeleteCategoriaController {
    @Autowired
    private ICategoriaService modelService;

    @DeleteMapping("/categoria/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        /*
         * Validaciones:
         * 1) Que el id se haya enviado.
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 2) Que el id sea un entero.
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 3) Que exista una categoria con dicho id.
         *   Se realiza la busqueda del obj y si el mismo retorna null se devuelve el badrequest
         * 4) Que la categoria encontrada no este eliminada.
         *   Si se encuentra la categoria, y la misma esta elimianda se retorna un badrequest.
         * En caso de que pase todas las verificacioens se cambia el la fechaEliminacion por el valor actual de tiempo.
         * Validaciones Futuras:
         * 1) Que la categoria que no tenga asociado ningun producto para poder eliminarlo.
         * 2) Fixear, la exp reg debe recibir cualquier caracter no solo letras
         * 3) Ademas si la exp falla debe poder resolverlo, por ejemplo si hay espacios
         * demas los debe quitar.
         * */
        try {
            Categoria model = modelService.buscarPorId(id);
            if (model == null) {
                ApiResponse<Object> response = new ApiResponse<>(
                        404,
                        "Error: Not Found.",
                        null,
                        "El id no corresponde a ninguna categoria, se debe enviar el id de una categoria existente."
                );
                return ResponseEntity.badRequest().body(response);
            }
            if (model.getFechaEliminacion() != null) {
                ApiResponse<Object> response = new ApiResponse<>(
                        400,
                        "Error: Bad Request.",
                        null,
                        "La categoria ya se encuentra eliminada, se debe enviar el id de una categoria no eliminada."
                );
                return ResponseEntity.badRequest().body(response);
            }
            //Queda pendiente la validacion de si esta asociada algun producto no se puede eliminar

            model.eliminar();
            modelService.eliminar(model);
            ApiResponse<Object> response = new ApiResponse<>(
                    200,
                    "OK.",
                    model,
                    null
            );
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
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
                400,
                "Error de tipo de argumento",
                null,
                error
        );

        return ResponseEntity.badRequest().body(response);
    }
}
