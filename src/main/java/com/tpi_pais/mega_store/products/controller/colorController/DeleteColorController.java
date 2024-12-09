package com.tpi_pais.mega_store.products.controller.colorController;

import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.model.Color;
import com.tpi_pais.mega_store.products.service.IColorService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class DeleteColorController {

    private final IColorService modelService;

    private final ResponseService responseService;

    public DeleteColorController(IColorService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    @DeleteMapping("/color/{id}")
    public ResponseEntity<ApiResponse<Object>>  eliminar(@PathVariable Integer id) {
        Color model = modelService.buscarPorId(id);
        modelService.eliminar(model);
        return responseService.successResponse(model, "Objeto eliminado");
    }
}

