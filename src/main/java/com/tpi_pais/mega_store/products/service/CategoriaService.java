package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.auth.dto.RolDTO;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.NotFoundException;
import com.tpi_pais.mega_store.products.dto.CategoriaDTO;
import com.tpi_pais.mega_store.products.mapper.CategoriaMapper;
import com.tpi_pais.mega_store.products.model.Categoria;
import com.tpi_pais.mega_store.products.repository.CategoriaRepository;
import com.tpi_pais.mega_store.utils.ExpresionesRegulares;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class CategoriaService implements ICategoriaService {
    
    @Autowired
    private CategoriaRepository modelRepository;

    @Override
    public List<CategoriaDTO> listar() {
        List<Categoria> categorias = modelRepository.findByFechaEliminacionIsNullOrderByIdAsc();
        return categorias.stream().map(CategoriaMapper::toDTO).toList();
    }

    @Override
    public Categoria buscarPorId(Integer id) {
        Optional<Categoria> model = modelRepository.findByIdAndFechaEliminacionIsNull(id);
        if (model.isEmpty()) {
            throw new NotFoundException("La categoria con id " + id + " no existe o se encuentra eliminada.");
        }
        return model.get();
    }

    @Override
    public Categoria buscarEliminadoPorId(Integer id) {
        Optional<Categoria> model = modelRepository.findByIdAndFechaEliminacionIsNotNull(id);
        if (model.isEmpty()) {
            throw new NotFoundException("La categoria con id " + id + " no existe o no se encuentra eliminda.");
        }
        return model.get();
    }

    @Override
    public Categoria buscarPorNombre(String nombre) {
        return modelRepository.findByNombre(nombre).orElse(null);
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

    @Override
    public CategoriaDTO verificarAtributos (CategoriaDTO categoriaDTO) {
        if (categoriaDTO.noTieneNombre()) {
            throw new BadRequestException("La categoria no tiene nombre");
        }
        ExpresionesRegulares expReg = new ExpresionesRegulares();
        if (!expReg.verificarCaracteres(categoriaDTO.getNombre())){
            throw new BadRequestException("El nombre enviado contiene caracteres no permitidos.");
        }
        if (!expReg.verificarTextoConEspacios(categoriaDTO.getNombre())){
            categoriaDTO.setNombre(expReg.corregirCadena(categoriaDTO.getNombre()));
            if (Objects.equals(categoriaDTO.getNombre(), "")){
                throw new BadRequestException("El nombre tiene un formato incorrecto");
            }
        }
        categoriaDTO.capitalizarNombre();
        return categoriaDTO;
    }

    @Override
    public boolean categoriaExistente(String nombre) {
        return modelRepository.findByNombre(nombre).isPresent();
    }
}