package com.tpi_pais.mega_store.auth.controller.UsuarioController;

import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.mapper.UsuarioMapper;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.service.IUsuarioService;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import com.tpi_pais.mega_store.utils.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping ("/auth")

public class PutUsuarioController {

    private final IUsuarioService modelService;

    private final ResponseService responseService;

    public PutUsuarioController(IUsuarioService modelService, ResponseService responseService, StringUtils stringUtils) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    @PutMapping("/usuario")
    public ResponseEntity<ApiResponse<Object>>  actualizar(@RequestBody UsuarioDTO modelDTO) {
        Boolean huboCambios = false;
        Usuario model = modelService.buscarPorId(modelDTO.getId());
        if (!Objects.equals(modelDTO.getNombre(), "") && modelDTO.getNombre() != null) {
            modelService.verificarNombre(modelDTO.getNombre(), "PUT");
            model.setNombre(modelDTO.getNombre());
            huboCambios = true;
        }
        if (!Objects.equals(modelDTO.getDireccionEnvio(), "") && modelDTO.getDireccionEnvio() != null) {
            modelService.verificarDireccion(modelDTO.getDireccionEnvio(), "PUT");
            model.setDireccionEnvio(modelDTO.getDireccionEnvio());
            huboCambios = true;
        }
        if (!Objects.equals(modelDTO.getTelefono(), "") && modelDTO.getTelefono() != null) {
            modelService.verificarTelefono(modelDTO.getTelefono(), "PUT");
            model.setTelefono(modelDTO.getTelefono());
            huboCambios = true;
        }
        if (huboCambios) {
            modelService.guardar(model);
            return responseService.successResponse(UsuarioMapper.toDTO(model), "Usuario actualizado");
        }else {
            throw new BadRequestException("No hubieron cambios");
        }
    }
}
