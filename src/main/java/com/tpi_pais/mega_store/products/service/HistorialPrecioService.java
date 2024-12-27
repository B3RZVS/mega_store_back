package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.auth.model.Sesion;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.service.SesionService;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.MessagesException;
import com.tpi_pais.mega_store.exception.NotFoundException;
import com.tpi_pais.mega_store.products.dto.HistorialPrecioDTO;
import com.tpi_pais.mega_store.products.model.HistorialPrecio;
import com.tpi_pais.mega_store.products.model.Producto;
import com.tpi_pais.mega_store.products.repository.HistorialPrecioRespository;
import com.tpi_pais.mega_store.utils.StringUtils;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Service
public class HistorialPrecioService implements IHistorialPrecioService{
    private final HistorialPrecioRespository repository;
    private final ProductoService productoService;
    private final SesionService sesionService;

    public HistorialPrecioService(HistorialPrecioRespository repository, ProductoService productoService, SesionService sesionService) {
        this.repository = repository;
        this.productoService = productoService;
        this.sesionService = sesionService;
    }

    @Override
    public void crear(BigDecimal precio, Producto producto, String token){
        this.verificarPrecio(precio);
        Usuario usuario = this.obtenerUsuario(token);
        HistorialPrecio model = new HistorialPrecio();
        model.setPrecio(precio);
        model.setUsuario(usuario);
        model.setProducto(producto);
        this.repository.save(model);
    }

    @Override
    public void verificarAtributos(HistorialPrecioDTO modelDto){
        this.verificarPrecio(modelDto.getPrecio());
    };

    @Override
    public void verificarPrecio(BigDecimal precio){
        if (precio == null || precio.compareTo(BigDecimal.valueOf(0.01)) < 0){
            throw new BadRequestException(MessagesException.CAMPO_NUMERICO_MAYOR_0+"precio");
        }
    };

    @Override
    public Usuario obtenerUsuario(String token){
        Sesion sesion = sesionService.obtenerSesionPorToken(token);
        return sesion.getUsuario();
    }

    @Override
    public Producto obtenerProducto(Integer productoId){
        return this.productoService.buscarPorId(productoId);
    }

    @Override
    public HistorialPrecio obtenerActual (Integer productoId){
        Optional<HistorialPrecio> model = this.repository.findFirstByProductoIdOrderByFechaDesc(productoId);
        if (model.isEmpty()){
            throw new NotFoundException(MessagesException.OBJECTO_NO_ENCONTRADO);
        } else {
            return model.get();
        }
    };

    @Override
    public List<HistorialPrecioDTO> listarPorProducto(Integer productoId){
        List<HistorialPrecio> models = this.repository.findByProductoIdOrderByFechaDesc(productoId);
        if (models.isEmpty()){
            throw new NotFoundException(MessagesException.OBJECTO_NO_ENCONTRADO);
        }
        return models.stream().map(model -> new HistorialPrecioDTO(model.getId(), model.getPrecio(), model.getFecha(), model.getUsuario().getId(), model.getProducto().getId())).toList();
    };

    @Override
    public List<HistorialPrecioDTO> listarPorUsuario(Integer usuarioId){
        List<HistorialPrecio> models = this.repository.findByUsuarioIdOrderByFechaDesc(usuarioId);
        if (models.isEmpty()){
            throw new NotFoundException(MessagesException.OBJECTO_NO_ENCONTRADO);
        }
        return models.stream().map(model -> new HistorialPrecioDTO(model.getId(), model.getPrecio(), model.getFecha(), model.getUsuario().getId(), model.getProducto().getId())).toList();
    };
}
