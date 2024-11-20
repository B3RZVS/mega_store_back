package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.products.dto.ColorDTO;
import com.tpi_pais.mega_store.products.model.Color;

import java.util.List;

public interface IColorService {
    public List<ColorDTO> listar();

    public Color buscarPorId(Integer id);

    public Color buscarEliminadoPorId(Integer id);

    public Color buscarPorNombre (String nombre);

    public ColorDTO guardar(ColorDTO model);

    public Color guardar(Color model);

    public void eliminar(Color model);

    public void recuperar(Color model);

    public ColorDTO verificarAtributos (ColorDTO colorDTO);

    public boolean colorExistente (String nombre);
}
