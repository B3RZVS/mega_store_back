package com.tpi_pais.mega_store.auth.controller.UsuarioController;

import com.tpi_pais.mega_store.auth.dto.RolDTO;
import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.model.Rol;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.service.IRolService;
import com.tpi_pais.mega_store.auth.service.IUsuarioService;
import com.tpi_pais.mega_store.exception.ResponseService;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class PostUsuarioController {
    @Autowired
    private IUsuarioService modelService;

    @Autowired
    private ResponseService responseService;

    @PostMapping("/usuario")
    public ResponseEntity<?> guardar(@RequestBody UsuarioDTO model){
        /*
        String nombre; -
        String email; -
        String telefono; 
        String direccionEnvio;
        LocalDateTime fechaCreacion; -
        String codigoVerificacion;
        Boolean verificado;
        String password;
        Long rolId;
        LocalDateTime fechaEliminacion;
        */

        Usuario modelu = modelService.verificarAtributos(model);
        return responseService.successResponse("Se ha registrado el usuario");
    }
}
