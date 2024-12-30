package com.tpi_pais.mega_store.auth.controller.AuthController;

import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.mapper.SesionMapper;
import com.tpi_pais.mega_store.auth.model.Rol;
import com.tpi_pais.mega_store.auth.model.Sesion;
import com.tpi_pais.mega_store.auth.service.IUsuarioService;
import com.tpi_pais.mega_store.auth.service.SesionService;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class LoginController {

    private final IUsuarioService modelService;

    private final ResponseService responseService;

    public LoginController(IUsuarioService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody UsuarioDTO usuarioDTO) {
        /*
        * El login debe recibir:
        * Un UsuarioDTO con lo siguiente:
        * - email
        * - password
        *
        * Debe verificar:
        *  - Que el usuario exista
        *  - Que este activo.
        *  - Que el password sea correcto
        *  - Que no este eliminado
        *
        * Una vez verificado se debe buscar o generar una sesion activa
        * y retornar el token correspondiente.
        * */
        Sesion sesion = modelService.login(usuarioDTO);
        return responseService.successResponse(SesionMapper.toDTO(sesion,1), "Sesion creada");
    }
}
