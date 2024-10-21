package com.tpi_pais.mega_store.auth.service;

import com.tpi_pais.mega_store.auth.repository.RolRepository;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.NotFoundException;
import com.tpi_pais.mega_store.utils.ApiResponse;
import com.tpi_pais.mega_store.utils.ExpresionesRegulares;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.tpi_pais.mega_store.auth.dto.RolDTO;
import com.tpi_pais.mega_store.auth.mapper.RolMapper;
import com.tpi_pais.mega_store.auth.model.Rol;

import java.util.List;
import java.util.Optional;

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
        Optional<Rol> model = modelRepository.findByIdAndFechaEliminacionIsNull(id);

        if (model.isEmpty()) {
            throw new NotFoundException("El rol con el id " + id + " no existe");
        }
        return model.get();
    }

    @Override
    public Rol buscarEliminadoPorId(Integer id) {
        Optional<Rol> model = modelRepository.findByIdAndFechaEliminacionIsNotNull(id);
        if (model.isEmpty()) {
            throw new NotFoundException("El rol con el id " + id + " no existe o no se encuentra eliminado");
        }
        return model.get();
    }

    @Override
    public Rol buscarPorNombre(String nombre) {
        Optional<Rol> model = modelRepository.findByNombreAndFechaEliminacionIsNull(nombre);
        if (model.isEmpty()) {
            throw new NotFoundException("El rol con el nombre " + nombre + " no existe o se encuentra eliminado");
        }
        return model.get();
    }

    @Override
    public Rol buscarEliminadoPorNombre(String nombre) {
        Optional<Rol> model = modelRepository.findByNombreAndFechaEliminacionIsNotNull(nombre);
        if (model.isEmpty()) {
            throw new NotFoundException("El rol con el nombre " + nombre + " no existe o no se encuentra eliminado");
        }
        return model.get();
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

    @Override
    public RolDTO verificarAtributos (RolDTO rolDTO) {
        if (rolDTO.noTieneNombre()) {
            throw new BadRequestException("El rol no tiene nombre");
        }
        ExpresionesRegulares expReg = new ExpresionesRegulares();
        if (!expReg.verificarCaracteres(rolDTO.getNombre())){
            throw new BadRequestException("El nombre enviado contiene caracteres no permitidos.");
        }
        if (!expReg.verificarTextoConEspacios(rolDTO.getNombre())){
            rolDTO.setNombre(expReg.corregirCadena(rolDTO.getNombre()));
            if (rolDTO.getNombre() == ""){
                throw new BadRequestException("El nombre tiene un formato incorrecto");
            }
        }
        rolDTO.capitalizarNombre();
        return rolDTO;
    }

    @Override
    public boolean rolExistente(String nombre) {
        return modelRepository.findByNombre(nombre).isPresent();
    }
}
