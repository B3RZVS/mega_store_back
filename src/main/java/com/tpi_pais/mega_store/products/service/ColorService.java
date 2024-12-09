package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.MessagesException;
import com.tpi_pais.mega_store.exception.NotFoundException;
import com.tpi_pais.mega_store.products.dto.ColorDTO;
import com.tpi_pais.mega_store.products.mapper.ColorMapper;
import com.tpi_pais.mega_store.products.model.Color;
import com.tpi_pais.mega_store.products.repository.ColorRepository;
import com.tpi_pais.mega_store.utils.ExpresionesRegulares;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class ColorService implements IColorService{

    private final ColorRepository modelRepository;

    private final ExpresionesRegulares expresionesRegulares;

    public ColorService(ColorRepository modelRepository, ExpresionesRegulares expresionesRegulares) {
        this.modelRepository = modelRepository;
        this.expresionesRegulares = expresionesRegulares;
    }

    @Override
    public List<ColorDTO> listar() {
        List<Color> colors = modelRepository.findByFechaEliminacionIsNullOrderByIdAsc();
        return colors.stream().map(ColorMapper::toDTO).toList();
    }

    @Override
    public Color buscarPorId(Integer id) {
        Optional<Color> model = modelRepository.findByIdAndFechaEliminacionIsNull(id);
        if (model.isEmpty()) {
            throw new NotFoundException(MessagesException.OBJECTO_NO_ENCONTRADO);
        }
        return model.get();
    }

    @Override
    public Color buscarEliminadoPorId(Integer id) {
        Optional<Color> model = modelRepository.findByIdAndFechaEliminacionIsNotNull(id);
        if (model.isEmpty()) {
            throw new NotFoundException(MessagesException.OBJECTO_NO_ENCONTRADO);
        }
        return model.get();
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

    @Override
    public ColorDTO verificarAtributos (ColorDTO colorDTO) {
        if (colorDTO.noTieneNombre()) {
            throw new BadRequestException(MessagesException.CAMPO_NO_ENVIADO+"nombre");
        }

        if (!expresionesRegulares.verificarCaracteres(colorDTO.getNombre())){
            throw new BadRequestException("El nombre enviado contiene caracteres no permitidos.");
        }
        if (!expresionesRegulares.verificarTextoConEspacios(colorDTO.getNombre())){
            colorDTO.setNombre(expresionesRegulares.corregirCadena(colorDTO.getNombre()));
            if (Objects.equals(colorDTO.getNombre(), "")){
                throw new BadRequestException("El nombre tiene un formato incorrecto");
            }
        }
        colorDTO.capitalizarNombre();
        return colorDTO;
    }

    @Override
    public boolean colorExistente(String nombre) {
        return modelRepository.findByNombre(nombre).isPresent();
    }
}
