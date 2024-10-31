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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

public class GetUsuarioController {
    @Autowired
    private IUsuarioService modelService;

    @Autowired
    private ResponseService responseService;

    @GetMapping({"/usuarios"})
    public ResponseEntity<?> getAll() {
        List<UsuarioDTO> usuarios = modelService.listar();
        if (usuarios.isEmpty()) {
            throw new BadRequestException("No hay usuarios creados");
        }
        return responseService.successResponse(usuarios, "OK");
    }

    @GetMapping("/usuario/id/{id}")
    public ResponseEntity<?> getPorId(@PathVariable Integer id){
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
        UsuarioDTO modelDTO = UsuarioMapper.toDTO(model);
        return responseService.successResponse(modelDTO, "OK");

    }

    @GetMapping("/usuario/email/{email}")
    public ResponseEntity<?> getPorEmail(@PathVariable String email){
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
        UsuarioDTO modelDTO = UsuarioMapper.toDTO(model);
        return responseService.successResponse(modelDTO, "OK");
    }

}


