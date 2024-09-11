package com.tpi_pais.mega_store.service;

import com.tpi_pais.mega_store.dto.CategoriaDTO;
import com.tpi_pais.mega_store.mapper.CategoriaMapper;
import com.tpi_pais.mega_store.model.Categoria;
import com.tpi_pais.mega_store.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class CategoriaService implements ICategoriaService {
    
    @Autowired
    private CategoriaRepository modelRepository;

    @Override
    public List<CategoriaDTO> listar() {
        List<Categoria> categorias = modelRepository.findByFechaEliminacionIsNull();
        return categorias.stream().map(CategoriaMapper::toDTO).toList();
    }

    @Override
    public Categoria buscarPorId(Integer id) {

        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public CategoriaDTO guardar(CategoriaDTO modelDTO) {
        Categoria model = CategoriaMapper.toEntity(modelDTO);
        return CategoriaMapper.toDTO(modelRepository.save(model));
    }
    @Override
    public Categoria guardar(Categoria model) {
        return modelRepository.save(model);
    }

    @Override
    public void eliminar(Categoria model) {

        model.eliminar();
        modelRepository.save(model);
    }
    @Override
    public void recuperar(Categoria model) {
        model.recuperar();
        modelRepository.save(model);
    }
}