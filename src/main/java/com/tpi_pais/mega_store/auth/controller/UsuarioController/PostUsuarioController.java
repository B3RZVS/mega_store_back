package com.tpi_pais.mega_store.auth.controller.UsuarioController;

import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.mapper.UsuarioMapper;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.service.IUsuarioService;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class PostUsuarioController {

    private final IUsuarioService modelService;

    private final ResponseService responseService;

    public PostUsuarioController(IUsuarioService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    @PostMapping("/usuario")
    public ResponseEntity<ApiResponse<Object>>  guardar(@RequestBody UsuarioDTO modelDTO){
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
        return responseService.successResponse(UsuarioMapper.toDTO(model),"Se ha registrado el usuario");
    }

    @PostMapping("/usuario/verificar")
    public ResponseEntity<ApiResponse<Object>>  verificar(@RequestBody UsuarioDTO modelDTO){
        modelService.verificarCodigoVerificacion(modelDTO.getEmail(), modelDTO.getCodigoVerificacion());
        return responseService.successResponse(null,"Se ha verificado el usuario");
    }

}
