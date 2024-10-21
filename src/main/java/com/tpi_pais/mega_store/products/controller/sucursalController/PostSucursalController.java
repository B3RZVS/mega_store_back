package com.tpi_pais.mega_store.products.controller.sucursalController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import org.springframework.http.ResponseEntity;
import com.tpi_pais.mega_store.products.dto.SucursalDTO;
import com.tpi_pais.mega_store.products.model.Sucursal;
import com.tpi_pais.mega_store.products.service.ISucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PostSucursalController {
    @Autowired
    private ISucursalService modelService;
    @Autowired
    private ResponseService responseService;

    @PostMapping("/sucursal")
    public ResponseEntity<?> guardar(@RequestBody SucursalDTO model){
        model = modelService.verificarAtributos(model);
        if (modelService.sucursalExistente(model.getNombre())){
            Sucursal aux = modelService.buscarPorNombre(model.getNombre());
            if (aux.esEliminado()){
                modelService.recuperar(aux);
                return responseService.successResponse(model, "Ya existia un objeto igual en la base de datos, objeto recuperado");
            } else {
                throw new BadRequestException("Ya existe una sucursal con ese nombre");
            }
        } else {
            SucursalDTO modelGuardado = modelService.guardar(model);
            return responseService.successResponse(modelGuardado, "Sucursal guardada");
        }
    }
}

