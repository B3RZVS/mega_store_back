package com.tpi_pais.mega_store.products.controller.colorController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.ColorDTO;
import com.tpi_pais.mega_store.products.mapper.ColorMapper;
import com.tpi_pais.mega_store.products.model.Color;
import com.tpi_pais.mega_store.products.service.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class GetColorController {
    @Autowired
    private IColorService modelService;
    @Autowired
    private ResponseService responseService;
    @GetMapping({"/colores"})
    public ResponseEntity<?> getAll() {
        List<ColorDTO> colors = modelService.listar();
        if (colors.isEmpty()) {
            throw new BadRequestException("No hay colores creados");
        }
        return responseService.successResponse(colors, "OK");
    }

    @GetMapping("/color/{id}")
    public ResponseEntity<?> getPorId(@PathVariable Integer id){
        Color model = modelService.buscarPorId(id);
        ColorDTO modelDTO = ColorMapper.toDTO(model);
        return responseService.successResponse(modelDTO, "OK");
    }
}