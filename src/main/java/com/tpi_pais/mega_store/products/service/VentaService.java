package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.auth.model.Sesion;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.service.SesionService;
import com.tpi_pais.mega_store.products.dto.DetalleVentaDTO;
import com.tpi_pais.mega_store.products.dto.VentaDTO;
import com.tpi_pais.mega_store.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VentaService implements IVentaService {
    private final DetalleVentaService detalleVentaService;
    private final SesionService sesionService;
    private final StringUtils stringUtils;

    public VentaService(DetalleVentaService detalleVentaService, SesionService sesionService, StringUtils stringUtils) {
        this.detalleVentaService = detalleVentaService;
        this.sesionService = sesionService;
        this.stringUtils = stringUtils;
    }


    @Override
    public VentaDTO guardar(String token, ArrayList<DetalleVentaDTO> model) {
        Sesion sesion = sesionService.obtenerSesionPorToken(stringUtils.limpiarToken(token));
        Usuario usuario = sesion.getUsuario();

        ArrayList<DetalleVentaDTO> detalles = new ArrayList<>();
        Double totalVenta = 0.0;
        for (DetalleVentaDTO detalleVentaDTO : model) {
            detalleVentaService.verificarDetalle(detalleVentaDTO);

        }
        return null;
    }
}
