package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.NotFoundException;
import com.tpi_pais.mega_store.products.dto.MovimientoStockDTO;
import com.tpi_pais.mega_store.products.mapper.MovimientoStockMapper;
import com.tpi_pais.mega_store.products.model.MovimientoStock;
import com.tpi_pais.mega_store.products.model.Producto;
import com.tpi_pais.mega_store.products.repository.MovimientoStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MovimientoStockService implements IMovimientoStockService {
    @Autowired
    private MovimientoStockRepository repository;

    @Autowired
    private IProductoService productoService;

    @Override
    public MovimientoStockDTO guardar(MovimientoStockDTO model){
        Producto producto = productoService.buscarPorId(model.getIdProducto());
        this.verificarCantidad(model.getCantidad(), model.getEsEgreso(), producto);

        MovimientoStock movimientoStock = new MovimientoStock();
        movimientoStock.setProducto(producto);
        movimientoStock.setCantidad(model.getCantidad());
        movimientoStock.setEsEgreso(model.getEsEgreso());

        repository.save(movimientoStock);
        MovimientoStockMapper mapper = new MovimientoStockMapper();
        return mapper.toDTO(movimientoStock);
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
        MovimientoStockMapper mapper = new MovimientoStockMapper();
        return mapper.toDTO(movimientoStock);
    };

    @Override
    public List<MovimientoStockDTO> listarPorProducto(Integer productoId){
        Producto producto = productoService.buscarPorId(productoId);
        List<MovimientoStock> models = repository.findByProductoIdOrderByFechaCreacionDesc(producto.getId());
        if (models.isEmpty()){
            throw new NotFoundException("El producto con id " + productoId + " no tiene movimientos de stock.");
        }
        MovimientoStockMapper mapper = new MovimientoStockMapper();
        return models.stream().map(mapper::toDTO).toList();
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
        if (cantidad <= 0){
            throw new BadRequestException("La cantidad debe ser mayor a 0.");
        }

        if (esEgreso){
            if (cantidad > producto.getStockActual()){
                throw new BadRequestException("No hay suficiente stock para realizar la salida.");
            }
        }
    };
}

