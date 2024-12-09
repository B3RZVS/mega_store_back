package com.tpi_pais.mega_store.products.controller.categoriaController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.CategoriaDTO;
import com.tpi_pais.mega_store.products.model.Categoria;
import com.tpi_pais.mega_store.products.service.ICategoriaService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PutCategoriaController {

    private final ICategoriaService modelService;

    private final ResponseService responseService;

    public PutCategoriaController(ICategoriaService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }
    @PutMapping("/categoria")
    public ResponseEntity<ApiResponse<Object>>  actualizar(@RequestBody CategoriaDTO model){
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
    public ResponseEntity<ApiResponse<Object>>  recuperar(@PathVariable Integer id) {
        Categoria model = modelService.buscarEliminadoPorId(id);
        modelService.recuperar(model);
        return responseService.successResponse(model, "Categoria recuperado");
    }
}
