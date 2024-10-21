package com.tpi_pais.mega_store.products.controller.colorController;

import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.model.Color;
import com.tpi_pais.mega_store.products.service.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class DeleteColorController {
    @Autowired
    private IColorService modelService;
    @Autowired
    private ResponseService responseService;

    @DeleteMapping("/color/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Color model = modelService.buscarPorId(id);
        modelService.eliminar(model);
        return responseService.successResponse(model, "Objeto eliminado");
    }
}

