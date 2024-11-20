package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.auth.model.Sesion;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.service.SesionService;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.NotFoundException;
import com.tpi_pais.mega_store.products.dto.HistorialPrecioDTO;
import com.tpi_pais.mega_store.products.model.HistorialPrecio;
import com.tpi_pais.mega_store.products.model.Producto;
import com.tpi_pais.mega_store.products.repository.HistorialPrecioRespository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class HistorialPrecioService implements IHistorialPrecioService{
    @Autowired
    private HistorialPrecioRespository repository;
    @Override
    public HistorialPrecio crear(HistorialPrecioDTO modelDto, String token){
        this.verificarAtributos(modelDto);
        Usuario usuario = this.obtenerUsuario(token);
        Producto producto = this.obtenerProducto(modelDto.getProductoId());
        HistorialPrecio model = new HistorialPrecio();
        model.setPrecio(modelDto.getPrecio());
        model.setUsuario(usuario);
        model.setProducto(producto);
        return this.repository.save(model);
    };

    @Override
    public void verificarAtributos(HistorialPrecioDTO modelDto){
        this.verificarPrecio(modelDto.getPrecio());
    };

    @Override
    public void verificarPrecio(Double precio){
        if (precio == null || precio.compareTo(0.01) < 0){
            throw new BadRequestException("El precio debe ser mayor que 0.");
        }
    };

    @Override
    public Usuario obtenerUsuario(String token){
        SesionService sesionService = new SesionService();
        Sesion sesion = sesionService.obtenerSesionPorToken(token);
        return sesion.getUsuario();
    }

    @Override
    public Producto obtenerProducto(Integer productoId){
        ProductoService productoService = new ProductoService();
        return productoService.buscarPorId(productoId);
    }

    @Override
    public HistorialPrecio obtenerActual (Integer productoId){
        Optional<HistorialPrecio> model = this.repository.findFirstByProductoIdOrderByFechaDesc(productoId);
        if (model.isEmpty()){
            throw new NotFoundException("El producto con id " + productoId + " no tiene historial de precios.");
        } else {
            return model.get();
        }
    };

    @Override
    public List<HistorialPrecioDTO> listarPorProducto(Integer productoId){
        List<HistorialPrecio> models = this.repository.findByProductoIdOrderByFechaDesc(productoId);
        if (models.isEmpty()){
            throw new NotFoundException("El producto con id " + productoId + " no tiene historial de precios.");
        }
        return models.stream().map(model -> new HistorialPrecioDTO(model.getId(), model.getPrecio(), model.getFecha(), model.getUsuario().getId(), model.getProducto().getId())).toList();
    };

    @Override
    public List<HistorialPrecioDTO> listarPorUsuario(Integer usuarioId){
        List<HistorialPrecio> models = this.repository.findByUsuarioIdOrderByFechaDesc(usuarioId);
        if (models.isEmpty()){
            throw new NotFoundException("El usuario con id " + usuarioId + " no tiene historial de precios creados.");
        }
        return models.stream().map(model -> new HistorialPrecioDTO(model.getId(), model.getPrecio(), model.getFecha(), model.getUsuario().getId(), model.getProducto().getId())).toList();
    };
}
