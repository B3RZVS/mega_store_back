package com.tpi_pais.mega_store.service;

import com.tpi_pais.mega_store.dto.CategoriaDTO;
import com.tpi_pais.mega_store.model.Categoria;

import java.util.List;

public interface ICategoriaService {
    public List<CategoriaDTO> listar();

    public Categoria buscarPorId(Integer id);

    public CategoriaDTO guardar(CategoriaDTO model);

    public Categoria guardar(Categoria model);

    public void eliminar(Categoria model);

    public void recuperar(Categoria model);
}
