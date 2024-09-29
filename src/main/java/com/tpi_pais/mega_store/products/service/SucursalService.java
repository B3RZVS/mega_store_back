package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.products.dto.SucursalDTO;
import com.tpi_pais.mega_store.products.mapper.SucursalMapper;
import com.tpi_pais.mega_store.products.model.Sucursal;
import com.tpi_pais.mega_store.products.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService implements ISucursalService {
    @Autowired
    private SucursalRepository modelRepository;

    @Override
    public List<SucursalDTO> listar() {
        List<Sucursal> Sucursals = modelRepository.findByFechaEliminacionIsNullOrderByIdAsc();
        return Sucursals.stream().map(SucursalMapper::toDTO).toList();
    }

    @Override
    public Sucursal buscarPorId(Integer id) {
        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public Sucursal buscarPorNombre(String nombre) {
        return modelRepository.findByNombre(nombre).orElse(null);
    }



    @Override
    public SucursalDTO guardar(SucursalDTO modelDTO) {
        Sucursal model = SucursalMapper.toEntity(modelDTO);
        return SucursalMapper.toDTO(modelRepository.save(model));
    }
    @Override
    public Sucursal guardar(Sucursal model) {
        return modelRepository.save(model);
    }

    @Override
    public void eliminar(Sucursal model) {

        model.eliminar();
        modelRepository.save(model);
    }
    @Override
    public void recuperar(Sucursal model) {
        model.recuperar();
        modelRepository.save(model);
    }
    
}
