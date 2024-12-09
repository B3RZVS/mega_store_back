package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.MessagesException;
import com.tpi_pais.mega_store.exception.NotFoundException;
import com.tpi_pais.mega_store.products.dto.TalleDTO;
import com.tpi_pais.mega_store.products.dto.TalleDTO;
import com.tpi_pais.mega_store.products.mapper.TalleMapper;
import com.tpi_pais.mega_store.products.model.Talle;
import com.tpi_pais.mega_store.products.model.Talle;
import com.tpi_pais.mega_store.products.repository.TalleRepository;
import com.tpi_pais.mega_store.utils.ExpresionesRegulares;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TalleService implements ITalleService {

    private final TalleRepository modelRepository;

    public TalleService(TalleRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public List<TalleDTO> listar() {
        List<Talle> talles = modelRepository.findByFechaEliminacionIsNullOrderByIdAsc();
        return talles.stream().map(TalleMapper::toDTO).toList();
    }

    @Override
    public Talle buscarPorId(Integer id) {
        Optional<Talle> model = modelRepository.findByIdAndFechaEliminacionIsNull(id);
        if (model.isEmpty()) {
            throw new NotFoundException(MessagesException.OBJECTO_NO_ENCONTRADO);
        }
        return model.get();
    }

    @Override
    public Talle buscarEliminadoPorId(Integer id) {
        Optional<Talle> model = modelRepository.findByIdAndFechaEliminacionIsNotNull(id);
        if (model.isEmpty()) {
            throw new NotFoundException(MessagesException.OBJECTO_NO_ENCONTRADO);
        }
        return model.get();
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

    @Override
    public TalleDTO verificarAtributos (TalleDTO talleDTO) {
        if (talleDTO.noTieneNombre()) {
            throw new BadRequestException(MessagesException.CAMPO_NO_ENVIADO+"nombre");
        }
        ExpresionesRegulares expReg = new ExpresionesRegulares();
        if (!expReg.verificarCaracteres(talleDTO.getNombre())){
            throw new BadRequestException(MessagesException.CARACTERES_INVALIDOS+"nombre");
        }
        if (!expReg.verificarTextoConEspacios(talleDTO.getNombre())){
            talleDTO.setNombre(expReg.corregirCadena(talleDTO.getNombre()));
            if (Objects.equals(talleDTO.getNombre(), "")){
                throw new BadRequestException(MessagesException.FORMATO_INVALIDO+"nombre");
            }
        }
        talleDTO.capitalizarNombre();
        return talleDTO;
    }

    @Override
    public boolean talleExistente(String nombre) {
        return modelRepository.findByNombre(nombre).isPresent();
    }
}
