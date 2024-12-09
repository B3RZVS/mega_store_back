package com.tpi_pais.mega_store.products.controller.marcaController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.MarcaDTO;
import com.tpi_pais.mega_store.products.mapper.MarcaMapper;
import com.tpi_pais.mega_store.products.model.Marca;
import com.tpi_pais.mega_store.products.service.IMarcaService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class GetMarcaController {

    private final IMarcaService modelService;

    private final ResponseService responseService;

    public GetMarcaController(IMarcaService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    @GetMapping({"/marcas"})
    public ResponseEntity<ApiResponse<Object>>  getAll() {
        List<MarcaDTO> marcas = modelService.listar();
        if (marcas.isEmpty()) {
            throw new BadRequestException("No hay marcas creadas");
        }
        return responseService.successResponse(marcas, "OK");
    }

    @GetMapping("/marca/{id}")
    public ResponseEntity<ApiResponse<Object>>  getPorId(@PathVariable Integer id){
        return responseService.successResponse(MarcaMapper.toDTO(modelService.buscarPorId(id)), "OK");
    }
}