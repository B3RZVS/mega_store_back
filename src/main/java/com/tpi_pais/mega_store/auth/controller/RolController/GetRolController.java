package com.tpi_pais.mega_store.auth.controller.RolController;

import com.tpi_pais.mega_store.auth.dto.RolDTO;
import com.tpi_pais.mega_store.auth.mapper.RolMapper;
import com.tpi_pais.mega_store.auth.model.Rol;
import com.tpi_pais.mega_store.auth.service.IRolService;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class GetRolController {
    @Autowired
    private IRolService modelService;

    @Autowired
    private ResponseService responseService;


    @GetMapping({"/roles"})
    public ResponseEntity<?> getAll() {
        List<RolDTO> roles = modelService.listar();
        if (roles.isEmpty()) {
            throw new BadRequestException("No hay roles creados");
        }
        return responseService.successResponse(roles, "OK");
    }

    @GetMapping("/rol/id/{id}")
    public ResponseEntity<?> getPorId(@PathVariable Integer id){
        Rol model = modelService.buscarPorId(id);
        RolDTO modelDTO = RolMapper.toDTO(model);
        return responseService.successResponse(modelDTO, "OK");
    }
    @GetMapping("/rol/nombre/{nombre}")
    public ResponseEntity<?> getPorNombre(@PathVariable String nombre){
        Rol model = modelService.buscarPorNombre(nombre);
        RolDTO modelDTO = RolMapper.toDTO(model);
        return responseService.successResponse(modelDTO, "OK");
    }
}
