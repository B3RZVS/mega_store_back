package com.tpi_pais.mega_store.auth.controller.RolController;

import com.tpi_pais.mega_store.auth.dto.RolDTO;
import com.tpi_pais.mega_store.auth.model.Rol;
import com.tpi_pais.mega_store.auth.service.IRolService;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.MessagesException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class PostRolController {
    private final IRolService modelService;

    private final ResponseService responseService;

    public PostRolController(IRolService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    @PostMapping("/rol")
    public ResponseEntity<ApiResponse<Object>>  guardar(@RequestBody RolDTO model){
        model = modelService.verificarAtributos(model);
        if (modelService.rolExistente(model.getNombre())){
            Rol aux = modelService.buscarPorNombre(model.getNombre());
            if (aux.esEliminado()){
                modelService.recuperar(aux);
                return responseService.successResponse(model, MessagesException.OBJETO_DUPLICADO_RECUPERADO);
            } else {
                throw new BadRequestException(MessagesException.OBJETO_DUPLICADO);
            }
        } else {
            RolDTO modelGuardado = modelService.guardar(model);
            return responseService.successResponse(modelGuardado, "Rol guardado");
        }
    }
}