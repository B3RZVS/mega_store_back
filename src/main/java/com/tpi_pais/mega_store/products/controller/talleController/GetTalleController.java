package com.tpi_pais.mega_store.products.controller.talleController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.TalleDTO;
import com.tpi_pais.mega_store.products.mapper.TalleMapper;
import com.tpi_pais.mega_store.products.model.Talle;
import com.tpi_pais.mega_store.products.service.ITalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class GetTalleController {
    @Autowired
    private ITalleService modelService;
    @Autowired
    private ResponseService responseService;
    @GetMapping({"/talles"})
    public ResponseEntity<?> getAll() {
        List<TalleDTO> talles = modelService.listar();
        if (talles.isEmpty()) {
            throw new BadRequestException("No hay talles creados");
        }
        return responseService.successResponse(talles, "OK");
    }

    @GetMapping("/talle/{id}")
    public ResponseEntity<?> getPorId(@PathVariable Integer id){
        Talle model = modelService.buscarPorId(id);
        TalleDTO modelDTO = TalleMapper.toDTO(model);
        return responseService.successResponse(modelDTO, "OK");
    }
}
