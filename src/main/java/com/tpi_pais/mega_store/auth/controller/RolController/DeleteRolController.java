package com.tpi_pais.mega_store.auth.controller.RolController;

import com.tpi_pais.mega_store.auth.model.Rol;
import com.tpi_pais.mega_store.auth.service.IRolService;
import com.tpi_pais.mega_store.exception.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class DeleteRolController {
    @Autowired
    private IRolService modelService;

    @Autowired
    private ResponseService responseService;

    @DeleteMapping("/rol/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Rol model = modelService.buscarPorId(id);
        modelService.eliminar(model);
        return responseService.successResponse(model, "Objeto eliminado");
    }
}

