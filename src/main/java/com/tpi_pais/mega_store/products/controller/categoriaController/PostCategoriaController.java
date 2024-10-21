package com.tpi_pais.mega_store.products.controller.categoriaController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import org.springframework.http.ResponseEntity;
import com.tpi_pais.mega_store.products.dto.CategoriaDTO;
import com.tpi_pais.mega_store.products.model.Categoria;
import com.tpi_pais.mega_store.products.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PostCategoriaController {
    @Autowired
    private ICategoriaService modelService;
    @Autowired
    private ResponseService responseService;

    @PostMapping("/categoria")
    public ResponseEntity<?> guardar(@RequestBody CategoriaDTO model){
        model = modelService.verificarAtributos(model);
        if (modelService.categoriaExistente(model.getNombre())){
            Categoria aux = modelService.buscarPorNombre(model.getNombre());
            if (aux.esEliminado()){
                modelService.recuperar(aux);
                return responseService.successResponse(model, "Ya existia un objeto igual en la base de datos, objeto recuperado");
            } else {
                throw new BadRequestException("Ya existe una categoria con ese nombre");
            }
        } else {
            CategoriaDTO modelGuardado = modelService.guardar(model);
            return responseService.successResponse(modelGuardado, "Categoria guardada");
        }
    }
}
