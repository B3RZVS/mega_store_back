package com.tpi_pais.mega_store.auth.controller.UsuarioController;

import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.mapper.UsuarioMapper;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.service.IUsuarioService;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import com.tpi_pais.mega_store.utils.ExpresionesRegulares;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class GetUsuarioController {

    private final IUsuarioService modelService;

    private final ResponseService responseService;

    public GetUsuarioController(IUsuarioService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    @GetMapping({"/usuarios"})
    public ResponseEntity<ApiResponse<Object>>  getAll() {
        List<UsuarioDTO> usuarios = modelService.listar();
        if (usuarios.isEmpty()) {
            throw new BadRequestException("No hay usuarios creados");
        }
        return responseService.successResponse(usuarios, "OK");
    }

    @GetMapping("/usuario/id/{id}")
    public ResponseEntity<ApiResponse<Object>>  getPorId(@PathVariable Integer id){
        /*
         * Validaciones:
         * 1) Que el id se haya enviado.
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 2) Que el id sea un entero.
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 3) Que exista una usuario con dicho id.
         *   Se realiza la busqueda del obj y si el mismo retorna null se devuelve el badrequest
         * 4) Que la usuario encontrada no este eliminada.
         *   Si se encuentra la usuario, y la misma esta elimianda se retorna un badrequest.
         * En caso de que pase todas las verificacioens devuelve el recurso encontrado.
         * */
        Usuario model = modelService.buscarPorId(id);
        return responseService.successResponse(UsuarioMapper.toDTO(model), "OK");

    }

    @GetMapping("/usuario/email/{email}")
    public ResponseEntity<ApiResponse<Object>>  getPorEmail(@PathVariable String email){
        /*
         * Validaciones:
         * 1) Que el id se haya enviado.
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 2) Que el id sea un entero.
         *   En caso que falle se ejecuta el @ExceptionHandler
         * 3) Que exista una usuario con dicho id.
         *   Se realiza la busqueda del obj y si el mismo retorna null se devuelve el badrequest
         * 4) Que la usuario encontrada no este eliminada.
         *   Si se encuentra la usuario, y la misma esta elimianda se retorna un badrequest.
         * En caso de que pase todas las verificacioens devuelve el recurso encontrado.
         * */
        modelService.verificarEmail(email);
        Usuario model = modelService.buscarPorEmail(email);
        return responseService.successResponse(UsuarioMapper.toDTO(model), "OK");
    }

}


