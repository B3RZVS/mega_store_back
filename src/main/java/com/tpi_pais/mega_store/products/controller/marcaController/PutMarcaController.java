package com.tpi_pais.mega_store.products.controller.marcaController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.MarcaDTO;
import com.tpi_pais.mega_store.products.model.Marca;
import com.tpi_pais.mega_store.products.service.IMarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PutMarcaController {
    @Autowired
    private IMarcaService modelService;
    @Autowired
    private ResponseService responseService;

    @PutMapping("/marca")
    public ResponseEntity<?> actualizar(@RequestBody MarcaDTO model){
        Marca marcaModificar = modelService.buscarPorId(model.getId());
        MarcaDTO modelDTO = modelService.verificarAtributos(model);
        if (modelService.marcaExistente(modelDTO.getNombre())){
            throw new BadRequestException("Ya existe una marca con ese nombre");
        } else {
            MarcaDTO modelGuardado = modelService.guardar(model);
            return responseService.successResponse(modelGuardado, "Marca actualiazado");
        }
    }
    @PutMapping("/marca/recuperar/{id}")
    public ResponseEntity<?> recuperar(@PathVariable Integer id) {
        Marca model = modelService.buscarEliminadoPorId(id);
        modelService.recuperar(model);
        return responseService.successResponse(model, "Marca recuperado");
    }
}
