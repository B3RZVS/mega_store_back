package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.products.dto.SucursalDTO;
import com.tpi_pais.mega_store.products.dto.SucursalDTO;
import com.tpi_pais.mega_store.products.model.Sucursal;
import com.tpi_pais.mega_store.products.model.Sucursal;

import java.util.List;

public interface ISucursalService {
    
    public List<SucursalDTO> listar();

    public Sucursal buscarPorId(Integer id);

    public Sucursal buscarPorNombre (String nombre);

    public SucursalDTO guardar(SucursalDTO model);

    public Sucursal guardar(Sucursal model);

    public void eliminar(Sucursal model);

    public void recuperar(Sucursal model);
}
