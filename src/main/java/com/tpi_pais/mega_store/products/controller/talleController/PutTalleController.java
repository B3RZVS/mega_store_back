package com.tpi_pais.mega_store.products.controller.talleController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.TalleDTO;
import com.tpi_pais.mega_store.products.model.Talle;
import com.tpi_pais.mega_store.products.service.ITalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PutTalleController {
    @Autowired
    private ITalleService modelService;
    @Autowired
    private ResponseService responseService;

    @PutMapping("/talle")
    public ResponseEntity<?> actualizar(@RequestBody TalleDTO model){
        Talle talleModificar = modelService.buscarPorId(model.getId());
        TalleDTO modelDTO = modelService.verificarAtributos(model);
        if (modelService.talleExistente(modelDTO.getNombre())){
            throw new BadRequestException("Ya existe un Talle con ese nombre");
        } else {
            TalleDTO modelGuardado = modelService.guardar(model);
            return responseService.successResponse(modelGuardado, "Talle actualiazado");
        }
    }
    @PutMapping("/talle/recuperar/{id}")
    public ResponseEntity<?> recuperar(@PathVariable Integer id) {
        Talle model = modelService.buscarEliminadoPorId(id);
        modelService.recuperar(model);
        return responseService.successResponse(model, "Talle recuperado");
    }
}
