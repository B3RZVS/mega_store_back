package com.tpi_pais.mega_store.products.controller.colorController;
import com.tpi_pais.mega_store.products.dto.ColorDTO;
import com.tpi_pais.mega_store.products.model.Color;
import com.tpi_pais.mega_store.products.service.IColorService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import com.tpi_pais.mega_store.utils.ExpresionesRegulares;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
@RequestMapping("/products")
public class PostColorController {
    @Autowired
    private IColorService modelService;

    @PostMapping("/Color")
    public ResponseEntity<?> guardar(@RequestBody ColorDTO model){
        /*
         * Validaciones:
         * 1) Que se haya enviado un ColorDTO
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 2) Que el dto enviado tenga un nombre distinto de null o ""
         *   En caso que falle se retorna una badrequest
         * 3) En caso de que contenga un nombre verifico si coincide con la expresion regular determinada.
         *   Las condiciones son:
         *   - Debe estar formado solo por letras y/o espacios.
         *   - Puede contener espacios, pero solo entre las palabras, no al principio ni al final.
         *   - Puede contener 1 y solo 1 espacio entre 2 palabras.
         * Una vez pasado esto se debe capitalizar el nombre para estandarizar todas las Colors.
         * 4) Que no exista una Color con el nombre.
         *
         * */
        try {
            if (model.noTieneNombre()) {
                ApiResponse<Object> response = new ApiResponse<>(
                        400,
                        "Error: Bad Request.",
                        null,
                        "No se envio un nombre para la Color."
                );
                return ResponseEntity.badRequest().body(response);
            };
            ExpresionesRegulares expReg = new ExpresionesRegulares();

            if (!expReg.verificarTextoConEspacios(model.getNombre())){
                model.setNombre(expReg.corregirCadena(model.getNombre()));
                if (model.getNombre() == ""){
                    ApiResponse<Object> response = new ApiResponse<>(
                            400,
                            "Error: Bad Request.",
                            null,
                            "El nombre debe estar formado unicamente por letras."
                    );
                    return ResponseEntity.badRequest().body(response);
                }
            }
            model.capitalizarNombre();
            Color aux = modelService.buscarPorNombre(model.getNombre());
            if (aux != null){
                ApiResponse<Object> response = new ApiResponse<>(
                        400,
                        "Error: Bad Request.",
                        null,
                        "Ya existe una Color con este nombre, no pueden haber 2 Colors con el mismo nombre."
                );
                return ResponseEntity.badRequest().body(response);
            }
            ColorDTO modelGuardado = modelService.guardar(model);
            ApiResponse<Object> response = new ApiResponse<>(
                    201,
                    "Created.",
                    modelGuardado,
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
                200,
                "Error de tipo de argumento",
                null,
                error
        );

        return ResponseEntity.badRequest().body(response);
    }
}
