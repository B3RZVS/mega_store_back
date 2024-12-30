package com.tpi_pais.mega_store.products.controller.colorController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.ColorDTO;
import com.tpi_pais.mega_store.products.mapper.ColorMapper;
import com.tpi_pais.mega_store.products.model.Color;
import com.tpi_pais.mega_store.products.service.IColorService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class GetColorController {

    private final IColorService modelService;

    private final ResponseService responseService;

    public GetColorController(IColorService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }
    @GetMapping({"/colores"})
    public ResponseEntity<ApiResponse<Object>>  getAll() {
        List<ColorDTO> colors = modelService.listar();
        if (colors.isEmpty()) {
            throw new BadRequestException("No hay colores creados");
        }
        return responseService.successResponse(colors, "OK");
    }

    @GetMapping("/color/{id}")
    public ResponseEntity<ApiResponse<Object>>  getPorId(@PathVariable Integer id){
        Color model = modelService.buscarPorId(id);
        return responseService.successResponse(ColorMapper.toDTO(model), "OK");
    }
}