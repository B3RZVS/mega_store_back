package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.products.dto.MarcaDTO;
import com.tpi_pais.mega_store.products.mapper.MarcaMapper;
import com.tpi_pais.mega_store.products.model.Marca;
import com.tpi_pais.mega_store.products.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaService implements ICategoriaService {

    @Autowired
    private MarcaRepository modelRepository;

    @Override
    public List<MarcaDTO> listar() {
        List<Marca> marcas = modelRepository.findByFechaEliminacionIsNull();
        return marcas.stream().map(MarcaMapper::toDTO).toList();
    }

    @Override
    public Marca buscarPorId(Integer id) {
        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public Marca buscarPorNombre(String nombre) {
        return modelRepository.findByNombre(nombre).orElse(null);
    }



    @Override
    public MarcaDTO guardar(MarcaDTO modelDTO) {
        Marca model = MarcaMapper.toEntity(modelDTO);
        return MarcaMapper.toDTO(modelRepository.save(model));
    }
    @Override
    public Marca guardar(Marca model) {
        return modelRepository.save(model);
    }

    @Override
    public void eliminar(Marca model) {

        model.eliminar();
        modelRepository.save(model);
    }
    @Override
    public void recuperar(Marca model) {
        model.recuperar();
        modelRepository.save(model);
    }
}
