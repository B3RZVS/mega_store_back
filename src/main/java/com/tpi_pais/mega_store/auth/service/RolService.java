package com.tpi_pais.mega_store.auth.service;

import com.tpi_pais.mega_store.auth.repository.RolRepository;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.MessagesException;
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
import java.util.Objects;
import java.util.Optional;

@Service
public class RolService implements IRolService {

    private final RolRepository modelRepository;

    public RolService(RolRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public List<RolDTO> listar() {
        List<Rol> categorias = modelRepository.findByFechaEliminacionIsNullOrderByIdAsc();
        return categorias.stream().map(RolMapper::toDTO).toList();
    }

    @Override
    public Rol buscarPorId(Integer id) {
        Optional<Rol> model = modelRepository.findByIdAndFechaEliminacionIsNull(id);

        if (model.isEmpty()) {
            throw new NotFoundException(MessagesException.OBJECTO_NO_ENCONTRADO);
        }
        return model.get();
    }

    @Override
    public Rol buscarEliminadoPorId(Integer id) {
        Optional<Rol> model = modelRepository.findByIdAndFechaEliminacionIsNotNull(id);
        if (model.isEmpty()) {
            throw new NotFoundException(MessagesException.OBJECTO_NO_ENCONTRADO);
        }
        return model.get();
    }

    @Override
    public Rol buscarPorNombre(String nombre) {
        Optional<Rol> model = modelRepository.findByNombreAndFechaEliminacionIsNull(nombre);
        if (model.isEmpty()) {
            throw new NotFoundException(MessagesException.OBJECTO_NO_ENCONTRADO);
        }
        return model.get();
    }

    @Override
    public Rol buscarEliminadoPorNombre(String nombre) {
        Optional<Rol> model = modelRepository.findByNombreAndFechaEliminacionIsNotNull(nombre);
        if (model.isEmpty()) {
            throw new NotFoundException(MessagesException.OBJECTO_NO_ENCONTRADO);
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
            throw new BadRequestException(MessagesException.CAMPO_NO_ENVIADO+"nombre");
        }
        ExpresionesRegulares expReg = new ExpresionesRegulares();
        if (!expReg.verificarCaracteres(rolDTO.getNombre())){
            throw new BadRequestException(MessagesException.CARACTERES_INVALIDOS);
        }
        if (!expReg.verificarTextoConEspacios(rolDTO.getNombre())){
            rolDTO.setNombre(expReg.corregirCadena(rolDTO.getNombre()));
            if (Objects.equals(rolDTO.getNombre(), "")){
                throw new BadRequestException(MessagesException.FORMATO_INVALIDO);
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
