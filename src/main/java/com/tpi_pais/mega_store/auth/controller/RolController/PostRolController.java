package com.tpi_pais.mega_store.auth.controller.RolController;

import com.tpi_pais.mega_store.auth.dto.RolDTO;
import com.tpi_pais.mega_store.auth.model.Rol;
import com.tpi_pais.mega_store.auth.service.IRolService;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class PostRolController {
    @Autowired
    private IRolService modelService;
    @Autowired
    private ResponseService responseService;

    @PostMapping("/rol")
    public ResponseEntity<?> guardar(@RequestBody RolDTO model){
        model = modelService.verificarAtributos(model);
        if (modelService.rolExistente(model.getNombre())){
            Rol aux = modelService.buscarPorNombre(model.getNombre());
            if (aux.esEliminado()){
                modelService.recuperar(aux);
                return responseService.successResponse(model, "Ya existia un objeto igual en la base de datos, objeto recuperado");
            } else {
                throw new BadRequestException("Ya existe un rol con ese nombre");
            }
        } else {
            RolDTO modelGuardado = modelService.guardar(model);
            return responseService.successResponse(modelGuardado, "Rol guardado");
        }
    }
}