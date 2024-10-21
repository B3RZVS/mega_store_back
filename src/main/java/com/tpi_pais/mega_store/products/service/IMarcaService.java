package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.products.dto.ColorDTO;
import com.tpi_pais.mega_store.products.dto.MarcaDTO;
import com.tpi_pais.mega_store.products.model.Marca;

import java.util.List;

public interface IMarcaService {
    public List<MarcaDTO> listar();

    public Marca buscarPorId(Integer id);

    public Marca buscarEliminadoPorId(Integer id);

    public Marca buscarPorNombre (String nombre);

    public MarcaDTO guardar(MarcaDTO model);

    public Marca guardar(Marca model);

    public void eliminar(Marca model);

    public void recuperar(Marca model);

    public MarcaDTO verificarAtributos (MarcaDTO marcaDTO);

    public boolean marcaExistente (String nombre);
}
