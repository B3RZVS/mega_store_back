package com.tpi_pais.mega_store.auth.controller.RolController;

import com.tpi_pais.mega_store.auth.dto.RolDTO;
import com.tpi_pais.mega_store.auth.mapper.RolMapper;
import com.tpi_pais.mega_store.auth.model.Rol;
import com.tpi_pais.mega_store.auth.service.IRolService;
import com.tpi_pais.mega_store.configs.SessionRequired;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class GetRolController {
    private final IRolService modelService;

    private final ResponseService responseService;

    public GetRolController(IRolService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    @SessionRequired
    @GetMapping({"/roles"})
    public ResponseEntity<ApiResponse<Object>>  getAll() {
        List<RolDTO> roles = modelService.listar();
        if (roles.isEmpty()) {
            throw new BadRequestException("No hay roles creados");
        }
        return responseService.successResponse(roles, "OK");
    }

    @GetMapping("/rol/id/{id}")
    public ResponseEntity<ApiResponse<Object>>  getPorId(@PathVariable Integer id){
        return responseService.successResponse(RolMapper.toDTO(modelService.buscarPorId(id)), "OK");
    }
    @GetMapping("/rol/nombre/{nombre}")
    public ResponseEntity<ApiResponse<Object>>  getPorNombre(@PathVariable String nombre){
        return responseService.successResponse(RolMapper.toDTO(modelService.buscarPorNombre(nombre)), "OK");
    }
}
