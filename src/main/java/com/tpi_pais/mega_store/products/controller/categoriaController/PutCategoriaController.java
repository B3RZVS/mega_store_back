package com.tpi_pais.mega_store.products.controller.categoriaController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.CategoriaDTO;
import com.tpi_pais.mega_store.products.model.Categoria;
import com.tpi_pais.mega_store.products.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PutCategoriaController {
    @Autowired
    private ICategoriaService modelService;
    @Autowired
    private ResponseService responseService;

    @PutMapping("/categoria")
    public ResponseEntity<?> actualizar(@RequestBody CategoriaDTO model){
        Categoria categoriaModificar = modelService.buscarPorId(model.getId());
        CategoriaDTO modelDTO = modelService.verificarAtributos(model);
        if (modelService.categoriaExistente(modelDTO.getNombre())){
            throw new BadRequestException("Ya existe una categoria con ese nombre");
        } else {
            CategoriaDTO modelGuardado = modelService.guardar(model);
            return responseService.successResponse(modelGuardado, "Categoria actualiazado");
        }
    }
    @PutMapping("/categoria/recuperar/{id}")
    public ResponseEntity<?> recuperar(@PathVariable Integer id) {
        Categoria model = modelService.buscarEliminadoPorId(id);
        modelService.recuperar(model);
        return responseService.successResponse(model, "Categoria recuperado");
    }
}
