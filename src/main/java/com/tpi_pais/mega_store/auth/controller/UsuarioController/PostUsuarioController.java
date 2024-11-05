package com.tpi_pais.mega_store.auth.controller.UsuarioController;

import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.mapper.UsuarioMapper;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.service.IUsuarioService;
import com.tpi_pais.mega_store.exception.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class PostUsuarioController {
    @Autowired
    private IUsuarioService modelService;

    @Autowired
    private ResponseService responseService;

    @PostMapping("/usuario")
    public ResponseEntity<?> guardar(@RequestBody UsuarioDTO modelDTO){
        /*
        String nombre; -
        String email; -
        String telefono; -
        String direccionEnvio;-
        LocalDateTime fechaCreacion; -
        String codigoVerificacion;
        Boolean verificado;
        String password;
        Long rolId;-
        LocalDateTime fechaEliminacion;-
        */
        modelService.verificarAtributos(modelDTO);
        Usuario model = modelService.crearUsuario(modelDTO);
        modelService.enviarCodigoVerificacion(model.getEmail(), model.getCodigoVerificacion());
        UsuarioMapper usuarioMapper = new UsuarioMapper();
        return responseService.successResponse(usuarioMapper.toDTO(model),"Se ha registrado el usuario");
    }
}
