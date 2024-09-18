package com.tpi_pais.mega_store.products.controller;

import com.tpi_pais.mega_store.products.dto.ColorDTO;
import com.tpi_pais.mega_store.products.mapper.ColorMapper;
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
public class ColorController {
    @Autowired
    private IColorService modelService;

    @GetMapping({"/Colors"})
    public ResponseEntity<?> getAll() {
        ApiResponse<Object> response = new ApiResponse<>(
                200,
                "OK",
                modelService.listar(),
                null
        );
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/Color/{id}")
    public ResponseEntity<?> getPorId(@PathVariable Integer id){
        /*
         * Validaciones:
         * 1) Que el id se haya enviado.
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 2) Que el id sea un entero.
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 3) Que exista una Color con dicho id.
         *   Se realiza la busqueda del obj y si el mismo retorna null se devuelve el badrequest
         * 4) Que la Color encontrada no este eliminada.
         *   Si se encuentra la Color, y la misma esta elimianda se retorna un badrequest.
         * En caso de que pase todas las verificacioens devuelve el recurso encontrado.
         * */

        try {
            Color model = modelService.buscarPorId(id);

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

            ColorDTO modelDTO = ColorMapper.toDTO(model);
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
                ApiResponse<Object> response = new ApiResponse<>(
                        400,
                        "Error: Bad Request.",
                        null,
                        "El nombre debe estar formado unicamente por letras."
                );
                return ResponseEntity.badRequest().body(response);
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

    @PutMapping("/Color")
    public ResponseEntity<?> actualizar(@RequestBody ColorDTO model){
        /*
         * Validaciones:
         * 1) Que se haya enviado un ColorDTO
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 2) Que el dto enviado tenga un id distinto de null o 0
         *   En caso que falle se retorna una badrequest
         * 3) Que el id enviado corresponda a un objeto Color y que el mismo no este eliminado
         *   En caso que falle se retorna una badrequest
         * 4) Que el dto enviado tenga un nombre distinto de null o ""
         *   En caso que falle se retorna una badrequest
         * 5) En caso de que contenga un nombre verifico si coincide con la expresion regular determinada.
         *   Las condiciones son:
         *   - Debe estar formado solo por letras y/o espacios.
         *   - Puede contener espacios, pero solo entre las palabras, no al principio ni al final.
         *   - Puede contener 1 y solo 1 espacio entre 2 palabras.
         * Una vez pasado esto se debe capitalizar el nombre para estandarizar todas las Colors.
         * 6) Que el nuevo nombre no este registrado en otro objeto Color
         *   En caso que falle se retorna una badrequest
         * */
        //return modelService.guardar(model);
        try{
            Color ColorModificar = modelService.buscarPorId(model.getId());
            if (ColorModificar == null){
                ApiResponse<Object> response = new ApiResponse<>(
                        404,
                        "Error: Not Found.",
                        null,
                        "El id no corresponde a ninguna Color, se debe enviar el id de una Color existente."
                );
                return ResponseEntity.badRequest().body(response);
            } else {
                if (ColorModificar.esEliminado()){
                    ApiResponse<Object> response = new ApiResponse<>(
                            400,
                            "Error: Bad Request.",
                            null,
                            "La Color no se puede modificar debido a que se encuentra eliminada."
                    );
                    return ResponseEntity.badRequest().body(response);
                }
            }

            if (model.noTieneNombre()) {
                ApiResponse<Object> response = new ApiResponse<>(
                        400,
                        "Error: Bad Request.",
                        null,
                        "La Color debe tener un nombre."
                );
                return ResponseEntity.badRequest().body(response);
            };
            ExpresionesRegulares expReg = new ExpresionesRegulares();

            if (!expReg.verificarTextoConEspacios(model.getNombre())){
                ApiResponse<Object> response = new ApiResponse<>(
                        400,
                        "Error: Bad Request.",
                        null,
                        "El nombre debe estar formado unicamente por letras."
                );
                return ResponseEntity.badRequest().body(response);
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

        }catch (Exception e){
            ApiResponse<Object> response = new ApiResponse<>(
                    400,
                    "Error: Error inesperado.",
                    null,
                    ""+e
            );
            return ResponseEntity.badRequest().body(response);
        }

    }

    @DeleteMapping("/Color/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        /*
         * Validaciones:
         * 1) Que el id se haya enviado.
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 2) Que el id sea un entero.
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 3) Que exista una Color con dicho id.
         *   Se realiza la busqueda del obj y si el mismo retorna null se devuelve el badrequest
         * 4) Que la Color encontrada no este eliminada.
         *   Si se encuentra la Color, y la misma esta elimianda se retorna un badrequest.
         * En caso de que pase todas las verificacioens se cambia el la fechaEliminacion por el valor actual de tiempo.
         * */
        try {
            Color model = modelService.buscarPorId(id);
            if (model == null) {
                ApiResponse<Object> response = new ApiResponse<>(
                        404,
                        "Error: Not Found.",
                        null,
                        "El id no corresponde a ninguna Color, se debe enviar el id de una Color existente."
                );
                return ResponseEntity.badRequest().body(response);
            }
            if (model.getFechaEliminacion() != null) {
                ApiResponse<Object> response = new ApiResponse<>(
                        400,
                        "Error: Bad Request.",
                        null,
                        "La Color ya se encuentra eliminada, se debe enviar el id de una Color no eliminada."
                );
                return ResponseEntity.badRequest().body(response);
            }
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

    @PutMapping("/Color/recuperar/{id}")
    public ResponseEntity<?> recuperar(@PathVariable Integer id) {
        /*
         * Validaciones:
         * 1) Que el id se haya enviado.
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 2) Que el id sea un entero.
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 3) Que exista una Color con dicho id.
         *   Se realiza la busqueda del obj y si el mismo retorna null se devuelve el badrequest
         * 4) Que la Color encontrada este eliminada.
         *   Si se encuentra la Color, y la misma no esta elimianda se retorna un badrequest.
         * En caso de que pase todas las verificacioens se cambia el la fechaEliminacion por el valor null.
         * */
        try {
            Color model = modelService.buscarPorId(id);
            if (model == null) {
                ApiResponse<Object> response = new ApiResponse<>(
                        404,
                        "Error: Not Found.",
                        null,
                        "El id no corresponde a ninguna Color, se debe enviar el id de una Color existente.."
                );
                return ResponseEntity.badRequest().body(response);
            }
            if (model.getFechaEliminacion() == null) {
                ApiResponse<Object> response = new ApiResponse<>(
                        400,
                        "Error: Bad Request.",
                        null,
                        "La Color ya no se encuentra eliminada, se debe enviar el id de una Color eliminada."
                );
                return ResponseEntity.badRequest().body(response);
            }
            model.recuperar();
            modelService.recuperar(model);
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
                200,
                "Error de tipo de argumento",
                null,
                error
        );

        return ResponseEntity.badRequest().body(response);
    }
}
