package com.tpi_pais.mega_store.products.controller.marcaController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.MarcaDTO;
import com.tpi_pais.mega_store.products.mapper.MarcaMapper;
import com.tpi_pais.mega_store.products.model.Marca;
import com.tpi_pais.mega_store.products.service.IMarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class GetMarcaController {
    @Autowired
    private IMarcaService modelService;
    @Autowired
    private ResponseService responseService;
    @GetMapping({"/marcas"})
    public ResponseEntity<?> getAll() {
        List<MarcaDTO> marcas = modelService.listar();
        if (marcas.isEmpty()) {
            throw new BadRequestException("No hay marcas creadas");
        }
        return responseService.successResponse(marcas, "OK");
    }

    @GetMapping("/marca/{id}")
    public ResponseEntity<?> getPorId(@PathVariable Integer id){
        Marca model = modelService.buscarPorId(id);
        MarcaDTO modelDTO = MarcaMapper.toDTO(model);
        return responseService.successResponse(modelDTO, "OK");
    }
}