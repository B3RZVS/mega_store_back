package com.tpi_pais.mega_store.auth.controller.AuthController;

import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.mapper.SesionMapper;
import com.tpi_pais.mega_store.auth.model.Rol;
import com.tpi_pais.mega_store.auth.model.Sesion;
import com.tpi_pais.mega_store.auth.service.IUsuarioService;
import com.tpi_pais.mega_store.auth.service.SesionService;
import com.tpi_pais.mega_store.exception.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private IUsuarioService modelService;

    @Autowired
    private ResponseService responseService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO usuarioDTO) {
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
        SesionMapper mapper = new SesionMapper();
        SesionService service = new SesionService();
        Rol rol = service.obtenerRol(sesion);
        return responseService.successResponse(mapper.toDTO(sesion,rol), "Sesion creada");
    }
}
