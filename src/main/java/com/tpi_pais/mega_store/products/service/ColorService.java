package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.products.dto.CategoriaDTO;
import com.tpi_pais.mega_store.products.dto.ColorDTO;
import com.tpi_pais.mega_store.products.mapper.CategoriaMapper;
import com.tpi_pais.mega_store.products.mapper.ColorMapper;
import com.tpi_pais.mega_store.products.model.Categoria;
import com.tpi_pais.mega_store.products.model.Color;
import com.tpi_pais.mega_store.products.repository.CategoriaRepository;
import com.tpi_pais.mega_store.products.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ColorService implements IColorService{
    @Autowired
    private ColorRepository modelRepository;

    @Override
    public List<ColorDTO> listar() {
        List<Color> colors = modelRepository.findByFechaEliminacionIsNullOrderByIdAsc();
        return colors.stream().map(ColorMapper::toDTO).toList();
    }

    @Override
    public Color buscarPorId(Integer id) {
        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public Color buscarPorNombre(String nombre) {
        return modelRepository.findByNombre(nombre).orElse(null);
    }



    @Override
    public ColorDTO guardar(ColorDTO modelDTO) {
        Color model = ColorMapper.toEntity(modelDTO);
        return ColorMapper.toDTO(modelRepository.save(model));
    }
    @Override
    public Color guardar(Color model) {
        return modelRepository.save(model);
    }

    @Override
    public void eliminar(Color model) {

        model.eliminar();
        modelRepository.save(model);
    }
    @Override
    public void recuperar(Color model) {
        model.recuperar();
        modelRepository.save(model);
    }
}
