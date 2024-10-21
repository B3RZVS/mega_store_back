package com.tpi_pais.mega_store.products.service;


import com.tpi_pais.mega_store.products.dto.CategoriaDTO;
import com.tpi_pais.mega_store.products.model.Categoria;

import java.util.List;

public interface ICategoriaService {
    public List<CategoriaDTO> listar();

    public Categoria buscarPorId(Integer id);

    public Categoria buscarEliminadoPorId(Integer id);

    public Categoria buscarPorNombre (String nombre);

    public CategoriaDTO guardar(CategoriaDTO model);

    public Categoria guardar(Categoria model);

    public void eliminar(Categoria model);

    public void recuperar(Categoria model);

    public CategoriaDTO verificarAtributos (CategoriaDTO categoriaDTO);

    public boolean categoriaExistente (String nombre);
}
