package com.tpi_pais.mega_store.auth.controller.UsuarioController;

import com.tpi_pais.mega_store.auth.dto.RolDTO;
import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.service.IRolService;
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
    public ResponseEntity<?> guardar(@RequestBody UsuarioDTO model){
        Usuario modelu = modelService.verificarAtributos(model);
        return responseService.successResponse("Se ha registrado el usuario");
    }
}
