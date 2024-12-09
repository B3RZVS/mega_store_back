package com.tpi_pais.mega_store.auth.controller.RolController;

import com.tpi_pais.mega_store.auth.dto.RolDTO;
import com.tpi_pais.mega_store.auth.service.IRolService;
import com.tpi_pais.mega_store.auth.model.Rol;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class PutRolController {
    private final IRolService modelService;

    private final ResponseService responseService;

    public PutRolController(IRolService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    @PutMapping("/rol")
    public ResponseEntity<ApiResponse<Object>>  actualizar(@RequestBody RolDTO model){
        Rol rolModificar = modelService.buscarPorId(model.getId());
        RolDTO modelDTO = modelService.verificarAtributos(model);
        if (modelService.rolExistente(modelDTO.getNombre())){
            throw new BadRequestException("Ya existe un rol con ese nombre");
        } else {
            RolDTO modelGuardado = modelService.guardar(model);
            return responseService.successResponse(modelGuardado, "Rol actualiazado");
        }
    }
    @PutMapping("/rol/recuperar/{id}")
    public ResponseEntity<ApiResponse<Object>>  recuperar(@PathVariable Integer id) {
        Rol model = modelService.buscarEliminadoPorId(id);
        modelService.recuperar(model);
        return responseService.successResponse(model, "Rol recuperado");
    }
}
