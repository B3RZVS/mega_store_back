package com.tpi_pais.mega_store.products.controller.colorController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.ColorDTO;
import com.tpi_pais.mega_store.products.model.Color;
import com.tpi_pais.mega_store.products.service.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PutColorController {
    @Autowired
    private IColorService modelService;
    @Autowired
    private ResponseService responseService;

    @PutMapping("/color")
    public ResponseEntity<?> actualizar(@RequestBody ColorDTO model){
        Color colorModificar = modelService.buscarPorId(model.getId());
        ColorDTO modelDTO = modelService.verificarAtributos(model);
        if (modelService.colorExistente(modelDTO.getNombre())){
            throw new BadRequestException("Ya existe un color con ese nombre");
        } else {
            ColorDTO modelGuardado = modelService.guardar(model);
            return responseService.successResponse(modelGuardado, "Color actualiazado");
        }
    }
    @PutMapping("/color/recuperar/{id}")
    public ResponseEntity<?> recuperar(@PathVariable Integer id) {
        Color model = modelService.buscarEliminadoPorId(id);
        modelService.recuperar(model);
        return responseService.successResponse(model, "Color recuperado");
    }
}
