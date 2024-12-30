package com.tpi_pais.mega_store.auth.controller.AuthController;

import com.tpi_pais.mega_store.auth.dto.SesionDTO;
import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.mapper.SesionMapper;
import com.tpi_pais.mega_store.auth.model.Sesion;
import com.tpi_pais.mega_store.auth.service.ISesionService;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class LogoutController {
    private final ISesionService modelService;

    private final ResponseService responseService;

    public LogoutController(ISesionService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }


    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Object>>  login(@RequestBody SesionDTO sesionDTO) {
        /*
         * El login debe recibir:
         * Un UsuarioDTO con lo siguiente:
         * - email
         *
         * Debe verificar:
         *  - Que el usuario exista, este activo y verificado
         *  - Que exista una sesion activa para ese usuario
         *
         * Una vez verificado se debe buscar la sesion activa
         * y eliminarla
         * */
        modelService.eliminarSesion(sesionDTO.getToken());
        return responseService.successResponse(null, "Sesion eliminada");
    }
}