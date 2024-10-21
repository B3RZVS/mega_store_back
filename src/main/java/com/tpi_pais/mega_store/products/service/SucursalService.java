package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.NotFoundException;
import com.tpi_pais.mega_store.products.dto.SucursalDTO;
import com.tpi_pais.mega_store.products.dto.SucursalDTO;
import com.tpi_pais.mega_store.products.mapper.SucursalMapper;
import com.tpi_pais.mega_store.products.model.Sucursal;
import com.tpi_pais.mega_store.products.repository.SucursalRepository;
import com.tpi_pais.mega_store.utils.ExpresionesRegulares;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        Optional<Sucursal> model = modelRepository.findByIdAndFechaEliminacionIsNull(id);
        if (model.isEmpty()) {
            throw new NotFoundException("La sucursal con id " + id + " no existe o se encuentra eliminada.");
        }
        return model.get();
    }

    @Override
    public Sucursal buscarEliminadoPorId(Integer id) {
        Optional<Sucursal> model = modelRepository.findByIdAndFechaEliminacionIsNotNull(id);
        if (model.isEmpty()) {
            throw new NotFoundException("La sucursal con id " + id + " no existe o no se encuentra eliminda.");
        }
        return model.get();
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

    @Override
    public SucursalDTO verificarAtributos (SucursalDTO sucursalDTO) {
        if (sucursalDTO.noTieneNombre()) {
            throw new BadRequestException("La sucursal no tiene nombre");
        }
        ExpresionesRegulares expReg = new ExpresionesRegulares();
        if (!expReg.verificarCaracteres(sucursalDTO.getNombre())){
            throw new BadRequestException("El nombre enviado contiene caracteres no permitidos.");
        }
        if (!expReg.verificarTextoConEspacios(sucursalDTO.getNombre())){
            sucursalDTO.setNombre(expReg.corregirCadena(sucursalDTO.getNombre()));
            if (Objects.equals(sucursalDTO.getNombre(), "")){
                throw new BadRequestException("El nombre tiene un formato incorrecto");
            }
        }
        sucursalDTO.capitalizarNombre();
        return sucursalDTO;
    }

    @Override
    public boolean sucursalExistente(String nombre) {
        return modelRepository.findByNombre(nombre).isPresent();
    }
}
