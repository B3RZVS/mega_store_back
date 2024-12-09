package com.tpi_pais.mega_store.products.controller.marcaController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.MarcaDTO;
import com.tpi_pais.mega_store.products.model.Marca;
import com.tpi_pais.mega_store.products.service.IMarcaService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PostMarcaController {

    private final IMarcaService modelService;

    private final ResponseService responseService;

    public PostMarcaController(IMarcaService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    @PostMapping("/marca")
    public ResponseEntity<ApiResponse<Object>>  guardar(@RequestBody MarcaDTO model){
        model = modelService.verificarAtributos(model);
        if (modelService.marcaExistente(model.getNombre())){
            Marca aux = modelService.buscarPorNombre(model.getNombre());
            if (aux.esEliminado()){
                modelService.recuperar(aux);
                return responseService.successResponse(model, "Ya existia un objeto igual en la base de datos, objeto recuperado");
            } else {
                throw new BadRequestException("Ya existe una marca con ese nombre");
            }
        } else {
            MarcaDTO modelGuardado = modelService.guardar(model);
            return responseService.successResponse(modelGuardado, "Marca guardada");
        }
    }
}
