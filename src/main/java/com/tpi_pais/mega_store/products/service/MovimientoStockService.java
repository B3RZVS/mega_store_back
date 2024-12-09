package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.MessagesException;
import com.tpi_pais.mega_store.exception.NotFoundException;
import com.tpi_pais.mega_store.products.dto.MovimientoStockDTO;
import com.tpi_pais.mega_store.products.mapper.MovimientoStockMapper;
import com.tpi_pais.mega_store.products.model.MovimientoStock;
import com.tpi_pais.mega_store.products.model.Producto;
import com.tpi_pais.mega_store.products.repository.MovimientoStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MovimientoStockService implements IMovimientoStockService {

    private final MovimientoStockRepository repository;
    private final IProductoService productoService;
    private final MovimientoStockMapper movimientoStockMapper;

    public MovimientoStockService(MovimientoStockRepository repository, IProductoService productoService, MovimientoStockMapper movimientoStockMapper) {
        this.repository = repository;
        this.productoService = productoService;
        this.movimientoStockMapper = movimientoStockMapper;
    }

    @Override
    public MovimientoStockDTO guardar(MovimientoStockDTO model){
        Producto producto = productoService.buscarPorId(model.getIdProducto());
        this.verificarCantidad(model.getCantidad(), model.getEsEgreso(), producto);

        MovimientoStock movimientoStock = new MovimientoStock();
        movimientoStock.setProducto(producto);
        movimientoStock.setCantidad(model.getCantidad());
        movimientoStock.setEsEgreso(model.getEsEgreso());

        repository.save(movimientoStock);
        return this.movimientoStockMapper.toDTO(movimientoStock);
    };

    @Override
    public MovimientoStockDTO guardar (Integer productoId, Integer cantidad, Boolean esEgreso){
        Producto producto = productoService.buscarPorId(productoId);
        this.verificarCantidad(cantidad, esEgreso, producto);
        MovimientoStock movimientoStock = new MovimientoStock();
        movimientoStock.setProducto(producto);
        movimientoStock.setCantidad(cantidad);
        movimientoStock.setEsEgreso(esEgreso);

        repository.save(movimientoStock);
        return this.movimientoStockMapper.toDTO(movimientoStock);
    };

    @Override
    public List<MovimientoStockDTO> listarPorProducto(Integer productoId){
        Producto producto = productoService.buscarPorId(productoId);
        List<MovimientoStock> models = repository.findByProductoIdOrderByFechaCreacionDesc(producto.getId());
        if (models.isEmpty()){
            throw new NotFoundException(MessagesException.OBJECTO_NO_ENCONTRADO);
        }
        return models.stream().map(this.movimientoStockMapper::toDTO).toList();
    };

    @Override
    public Integer obtenerStockActual(Integer productoId){
        Producto producto = productoService.buscarPorId(productoId);

        List<MovimientoStock> models = repository.findByProductoIdOrderByFechaCreacionDesc(producto.getId());
        Integer stockActual = 0;
        for (MovimientoStock model : models){
            if (!model.getEsEgreso()){
                stockActual += model.getCantidad();
            } else {
                stockActual -= model.getCantidad();
            }
        }
        return stockActual;
    };

    @Override
    public Integer obtenerStockActual(Producto producto){
        List<MovimientoStock> models = repository.findByProductoIdOrderByFechaCreacionDesc(producto.getId());
        Integer stockActual = 0;
        for (MovimientoStock model : models){
            if (!model.getEsEgreso()){
                stockActual += model.getCantidad();
            } else {
                stockActual -= model.getCantidad();
            }
        }
        return stockActual;
    };

    @Override
    public void verificarCantidad(Integer cantidad, Boolean esEgreso, Producto producto){
        if (esEgreso != null && esEgreso){
            if (cantidad > producto.getStockActual()){
                throw new BadRequestException(MessagesException.STOCK_INSUFICIENTE);
            }
        }else {
            throw new BadRequestException("El campo esEgreso es obligatorio.");
        }
    };
}

