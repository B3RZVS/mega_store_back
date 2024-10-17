package com.tpi_pais.mega_store.auth.service;

import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.mapper.UsuarioMapper;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UsuarioService implements IUsuarioService{

    @Autowired
    private UsuarioRepository modelRepository;

    @Override
    public List<UsuarioDTO> listar() {
        List<Usuario> categorias = modelRepository.findByFechaEliminacionIsNullAndActivoTrueOrderByIdAsc();
        return categorias.stream().map(UsuarioMapper::toDTO).toList();
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario buscarPorNombre(String nombre) {
        return modelRepository.findByNombre(nombre).orElse(null);
    }


    @Override
    public UsuarioDTO guardar(UsuarioDTO modelDTO) {
        Usuario model = UsuarioMapper.toEntity(modelDTO);
        return UsuarioMapper.toDTO(modelRepository.save(model));
    }
    @Override
    public Usuario guardar(Usuario model) {
        return modelRepository.save(model);
    }

    @Override
    public void eliminar(Usuario model) {

        model.eliminar();
        modelRepository.save(model);
    }
    @Override
    public void recuperar(Usuario model) {
        model.recuperar();
        modelRepository.save(model);
    }

    @Override
    public Boolean checkPassword(Usuario model, String password) {
        return model.checkPassword(password);
    }

    @Override
    public void setPassword(Usuario model, String password) {
        if (password != null) {
            if (model.checkPassword(password)) {
                model.setPassword(password);
                modelRepository.save(model);
            }
        }
    }
    
}
