package com.tpi_pais.mega_store.products.controller.colorController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import org.springframework.http.ResponseEntity;
import com.tpi_pais.mega_store.products.dto.ColorDTO;
import com.tpi_pais.mega_store.products.model.Color;
import com.tpi_pais.mega_store.products.service.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PostColorController {
    @Autowired
    private IColorService modelService;
    @Autowired
    private ResponseService responseService;

    @PostMapping("/color")
    public ResponseEntity<?> guardar(@RequestBody ColorDTO model){
        model = modelService.verificarAtributos(model);
        if (modelService.colorExistente(model.getNombre())){
            Color aux = modelService.buscarPorNombre(model.getNombre());
            if (aux.esEliminado()){
                modelService.recuperar(aux);
                return responseService.successResponse(model, "Ya existia un objeto igual en la base de datos, objeto recuperado");
            } else {
                throw new BadRequestException("Ya existe un color con ese nombre");
            }
        } else {
            ColorDTO modelGuardado = modelService.guardar(model);
            return responseService.successResponse(modelGuardado, "Color guardado");
        }
    }

}