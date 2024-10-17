package com.tpi_pais.mega_store.auth.service;

import com.tpi_pais.mega_store.auth.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tpi_pais.mega_store.auth.dto.RolDTO;
import com.tpi_pais.mega_store.auth.mapper.RolMapper;
import com.tpi_pais.mega_store.auth.model.Rol;
import com.tpi_pais.mega_store.auth.service.IRolService;

import java.util.List;

@Service
public class RolService implements IRolService {

    @Autowired
    private RolRepository modelRepository;

    @Override
    public List<RolDTO> listar() {
        List<Rol> categorias = modelRepository.findByFechaEliminacionIsNullOrderByIdAsc();
        return categorias.stream().map(RolMapper::toDTO).toList();
    }

    @Override
    public Rol buscarPorId(Integer id) {
        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public Rol buscarPorNombre(String nombre) {
        return modelRepository.findByNombre(nombre).orElse(null);
    }


    @Override
    public RolDTO guardar(RolDTO modelDTO) {
        Rol model = RolMapper.toEntity(modelDTO);
        return RolMapper.toDTO(modelRepository.save(model));
    }
    @Override
    public Rol guardar(Rol model) {
        return modelRepository.save(model);
    }

    @Override
    public void eliminar(Rol model) {

        model.eliminar();
        modelRepository.save(model);
    }
    @Override
    public void recuperar(Rol model) {
        model.recuperar();
        modelRepository.save(model);
    }
}
