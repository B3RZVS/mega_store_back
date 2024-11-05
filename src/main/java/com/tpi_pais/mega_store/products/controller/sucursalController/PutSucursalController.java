package com.tpi_pais.mega_store.products.controller.sucursalController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.SucursalDTO;
import com.tpi_pais.mega_store.products.model.Sucursal;
import com.tpi_pais.mega_store.products.service.ISucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PutSucursalController {
    @Autowired
    private ISucursalService modelService;
    @Autowired
    private ResponseService responseService;

    @PutMapping("/sucursal")
    public ResponseEntity<?> actualizar(@RequestBody SucursalDTO model){
        Sucursal sucursalModificar = modelService.buscarPorId(model.getId());
        SucursalDTO modelDTO = modelService.verificarAtributos(model);
        if (modelService.sucursalExistente(modelDTO.getNombre())){
            throw new BadRequestException("Ya existe una Sucursal con ese nombre");
        } else {
            SucursalDTO modelGuardado = modelService.guardar(model);
            return responseService.successResponse(modelGuardado, "Sucursal actualiazado");
        }
    }
    @PutMapping("/sucursal/recuperar/{id}")
    public ResponseEntity<?> recuperar(@PathVariable Integer id) {
        Sucursal model = modelService.buscarEliminadoPorId(id);
        modelService.recuperar(model);
        return responseService.successResponse(model, "Sucursal recuperado");
    }
}
