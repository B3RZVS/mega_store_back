package com.tpi_pais.mega_store.auth.service;

import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.model.Usuario;

import java.util.List;

public interface IUsuarioService {
    public List<UsuarioDTO> listar();

    public Usuario buscarPorId(Integer id);

    public Usuario buscarPorNombre (String nombre);

    public UsuarioDTO guardar(UsuarioDTO model);

    public Usuario guardar(Usuario model);

    public void eliminar(Usuario model);

    public void recuperar(Usuario model);

    public Boolean checkPassword (Usuario model, String password);

    public void setPassword(Usuario model, String password);


}
