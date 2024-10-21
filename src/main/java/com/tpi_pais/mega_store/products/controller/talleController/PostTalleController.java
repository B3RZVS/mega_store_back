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
public class PostTalleController {
    @Autowired
    private ITalleService modelService;
    @Autowired
    private ResponseService responseService;

    @PostMapping("/talle")
    public ResponseEntity<?> guardar(@RequestBody TalleDTO model){
        model = modelService.verificarAtributos(model);
        if (modelService.talleExistente(model.getNombre())){
            Talle aux = modelService.buscarPorNombre(model.getNombre());
            if (aux.esEliminado()){
                modelService.recuperar(aux);
                return responseService.successResponse(model, "Ya existia un objeto igual en la base de datos, objeto recuperado");
            } else {
                throw new BadRequestException("Ya existe un talle con ese nombre");
            }
        } else {
            TalleDTO modelGuardado = modelService.guardar(model);
            return responseService.successResponse(modelGuardado, "Talle guardado");
        }
    }
}
