package com.tpi_pais.mega_store.products.controller.sucursalController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.SucursalDTO;
import com.tpi_pais.mega_store.products.mapper.SucursalMapper;
import com.tpi_pais.mega_store.products.model.Sucursal;
import com.tpi_pais.mega_store.products.service.ISucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class GetSucursalController {
    @Autowired
    private ISucursalService modelService;
    @Autowired
    private ResponseService responseService;
    @GetMapping({"/sucursales"})
    public ResponseEntity<?> getAll() {
        List<SucursalDTO> sucursals = modelService.listar();
        if (sucursals.isEmpty()) {
            throw new BadRequestException("No hay sucursales creadas");
        }
        return responseService.successResponse(sucursals, "OK");
    }

    @GetMapping("/sucursal/{id}")
    public ResponseEntity<?> getPorId(@PathVariable Integer id){
        Sucursal model = modelService.buscarPorId(id);
        SucursalDTO modelDTO = SucursalMapper.toDTO(model);
        return responseService.successResponse(modelDTO, "OK");
    }
}
