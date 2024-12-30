package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.products.dto.DetalleVentaDTO;
import com.tpi_pais.mega_store.products.dto.VentaDTO;

import java.util.ArrayList;
import java.util.List;

public interface IVentaService {

    public VentaDTO guardar (String token, ArrayList<DetalleVentaDTO> model);

}
