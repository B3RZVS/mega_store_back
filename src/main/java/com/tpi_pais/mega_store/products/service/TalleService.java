package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.products.dto.TalleDTO;
import com.tpi_pais.mega_store.products.mapper.TalleMapper;
import com.tpi_pais.mega_store.products.model.Talle;
import com.tpi_pais.mega_store.products.repository.TalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TalleService implements ITalleService {

    @Autowired
    private TalleRepository modelRepository;

    @Override
    public List<TalleDTO> listar() {
        List<Talle> talles = modelRepository.findByFechaEliminacionIsNull();
        return talles.stream().map(TalleMapper::toDTO).toList();
    }

    @Override
    public Talle buscarPorId(Integer id) {
        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public Talle buscarPorNombre(String nombre) {
        return modelRepository.findByNombre(nombre).orElse(null);
    }



    @Override
    public TalleDTO guardar(TalleDTO modelDTO) {
        Talle model = TalleMapper.toEntity(modelDTO);
        return TalleMapper.toDTO(modelRepository.save(model));
    }
    @Override
    public Talle guardar(Talle model) {
        return modelRepository.save(model);
    }

    @Override
    public void eliminar(Talle model) {

        model.eliminar();
        modelRepository.save(model);
    }
    @Override
    public void recuperar(Talle model) {
        model.recuperar();
        modelRepository.save(model);
    }
}
