package com.tpi_pais.mega_store.auth.controller.UsuarioController;

import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.service.IUsuarioService;
import com.tpi_pais.mega_store.exception.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class DeleteUsuarioController {
    @Autowired
    private IUsuarioService modelService;

    @Autowired
    private ResponseService responseService;

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        /*
         * Validaciones:
         * 1) Que el id se haya enviado.
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 2) Que el id sea un entero.
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 3) Que exista una Usuario con dicho id.
         *   Se realiza la busqueda del obj y si el mismo retorna null se devuelve el badrequest
         * 4) Que el Usuario encontrado no este eliminada.
         *   Si se encuentra el usuario, y el mismo esta elimiando se retorna un badrequest.
         * En caso de que pase todas las verificacioens se cambia el la fechaEliminacion por el valor actual de tiempo.
         * Validaciones Futuras:
         * 1) Que el usuario que no tenga asociado ningun producto para poder eliminarlo.
         *
         * */
        Usuario model = modelService.buscarEliminadoPorId(id);
        modelService.eliminar(model);
        return responseService.successResponse(model, "Objeto eliminado");
    }
}
