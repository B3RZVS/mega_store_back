package com.tpi_pais.mega_store.products.controller.marcaController;

import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.model.Marca;
import com.tpi_pais.mega_store.products.service.IMarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class DeleteMarcaController {
    @Autowired
    private IMarcaService modelService;
    @Autowired
    private ResponseService responseService;

    @DeleteMapping("/marca/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Marca model = modelService.buscarPorId(id);
        modelService.eliminar(model);
        return responseService.successResponse(model, "Objeto eliminado");
    }
}
