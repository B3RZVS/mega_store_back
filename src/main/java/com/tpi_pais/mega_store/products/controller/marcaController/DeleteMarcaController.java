package com.tpi_pais.mega_store.products.controller.marcaController;

import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.model.Marca;
import com.tpi_pais.mega_store.products.service.IMarcaService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class DeleteMarcaController {

    private final IMarcaService modelService;

    private final ResponseService responseService;

    public DeleteMarcaController(IMarcaService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    @DeleteMapping("/marca/{id}")
    public ResponseEntity<ApiResponse<Object>>  eliminar(@PathVariable Integer id) {
        Marca model = modelService.buscarPorId(id);
        modelService.eliminar(model);
        return responseService.successResponse(model, "Objeto eliminado");
    }
}
