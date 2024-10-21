package com.tpi_pais.mega_store.products.controller.talleController;

import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.model.Talle;
import com.tpi_pais.mega_store.products.service.ITalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class DeleteTalleController {
    @Autowired
    private ITalleService modelService;
    @Autowired
    private ResponseService responseService;

    @DeleteMapping("/talle/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Talle model = modelService.buscarPorId(id);
        modelService.eliminar(model);
        return responseService.successResponse(model, "Objeto eliminado");
    }
}