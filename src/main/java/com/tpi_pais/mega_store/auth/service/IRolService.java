package com.tpi_pais.mega_store.auth.service;

import com.tpi_pais.mega_store.auth.dto.RolDTO;
import com.tpi_pais.mega_store.auth.model.Rol;

import java.util.List;

public interface IRolService {
    public List<RolDTO> listar();

    public Rol buscarPorId(Integer id);

    public Rol buscarEliminadoPorId(Integer id);

    public Rol buscarPorNombre (String nombre);

    public Rol buscarEliminadoPorNombre(String nombre);

    public RolDTO guardar(RolDTO model);

    public Rol guardar(Rol model);

    public void eliminar(Rol model);

    public void recuperar(Rol model);

    public RolDTO verificarAtributos (RolDTO rolDTO);

    public boolean rolExistente (String nombre);

}

