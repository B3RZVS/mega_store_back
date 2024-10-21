package com.tpi_pais.mega_store.products.controller.categoriaController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.CategoriaDTO;
import com.tpi_pais.mega_store.products.mapper.CategoriaMapper;
import com.tpi_pais.mega_store.products.model.Categoria;
import com.tpi_pais.mega_store.products.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class GetCategoriaController {
    @Autowired
    private ICategoriaService modelService;
    @Autowired
    private ResponseService responseService;

    @GetMapping({"/categorias"})
    public ResponseEntity<?> getAll() {
        List<CategoriaDTO> categorias = modelService.listar();
        if (categorias.isEmpty()) {
            throw new BadRequestException("No hay categorias creadas");
        }
        return responseService.successResponse(categorias, "OK");
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<?> getPorId(@PathVariable Integer id){
        Categoria model = modelService.buscarPorId(id);
        CategoriaDTO modelDTO = CategoriaMapper.toDTO(model);
        return responseService.successResponse(modelDTO, "OK");
    }
}
