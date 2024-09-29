package com.tpi_pais.mega_store.products.controller.marcaController;

import com.tpi_pais.mega_store.products.dto.MarcaDTO;
import com.tpi_pais.mega_store.products.model.Marca;
import com.tpi_pais.mega_store.products.service.IMarcaService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import com.tpi_pais.mega_store.utils.ExpresionesRegulares;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/products")
public class PostMarcaController {
    @Autowired
    private IMarcaService modelService;

    @PostMapping("/marca")
    public ResponseEntity<?> guardar(@RequestBody MarcaDTO model){
        /*
         * Validaciones:
         * 1) Que se haya enviado un CategoriaDTO
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 2) Que el dto enviado tenga un nombre distinto de null o ""
         *   En caso que falle se retorna una badrequest
         * 3) En caso de que contenga un nombre verifico si coincide con la expresion regular determinada.
         *   Las condiciones son:
         *   - Debe estar formado solo por letras y/o espacios.
         *   - Puede contener espacios, pero solo entre las palabras, no al principio ni al final.
         *   - Puede contener 1 y solo 1 espacio entre 2 palabras.
         * Una vez pasado esto se debe capitalizar el nombre para estandarizar todas las categorias.
         * 4) Que no exista una categoria con el nombre.
         *
         * */
        try {
            if (model.noTieneNombre()) {
                ApiResponse<Object> response = new ApiResponse<>(
                        400,
                        "Error: Bad Request.",
                        null,
                        "No se envio un nombre para la marca."
                );
                return ResponseEntity.badRequest().body(response);
            };
            ExpresionesRegulares expReg = new ExpresionesRegulares();
            if (!expReg.verificarCaracteres(model.getNombre())){
                ApiResponse<Object> response = new ApiResponse<>(
                        400,
                        "Error: Bad Request.",
                        null,
                        "El nombre debe estar formado únicamente por letras y números."
                );
                return ResponseEntity.badRequest().body(response);
            }
            if (!expReg.verificarTextoConEspacios(model.getNombre())){
                model.setNombre(expReg.corregirCadena(model.getNombre()));
                if (model.getNombre() == ""){
                    ApiResponse<Object> response = new ApiResponse<>(
                            400,
                            "Error: Bad Request.",
                            null,
                            "El nombre debe estar formado unicamente por letras y numeros."
                    );
                    return ResponseEntity.badRequest().body(response);
                }
            }
            model.capitalizarNombre();
            Marca aux = modelService.buscarPorNombre(model.getNombre());
            if (aux != null){
                if (aux.esEliminado()){
                    modelService.recuperar(aux);
                    ApiResponse<Object> response = new ApiResponse<>(
                            201,
                            "Created.",
                            aux,
                            null
                    );
                    return ResponseEntity.ok().body(response);
                } else {
                    ApiResponse<Object> response = new ApiResponse<>(
                            400,
                            "Error: Bad Request.",
                            null,
                            "Ya existe una marca con este nombre."
                    );
                    return ResponseEntity.badRequest().body(response);
                }
            } else {
                MarcaDTO modelGuardado = modelService.guardar(model);
                ApiResponse<Object> response = new ApiResponse<>(
                        201,
                        "Created.",
                        modelGuardado,
                        null
                );
                return ResponseEntity.ok().body(response);
            }
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
        String error = String.format("El parámetro '%s' debe ser un DTO de Categoria valido.", ex.getName());
        ApiResponse<Object> response = new ApiResponse<>(
                400,
                "Error de tipo de argumento",
                null,
                error
        );
        return ResponseEntity.badRequest().body(response);
    }
}
