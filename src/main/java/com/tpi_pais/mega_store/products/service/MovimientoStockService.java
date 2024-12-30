package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.MessagesException;
import com.tpi_pais.mega_store.exception.NotFoundException;
import com.tpi_pais.mega_store.products.dto.MovimientoStockDTO;
import com.tpi_pais.mega_store.products.mapper.MovimientoStockMapper;
import com.tpi_pais.mega_store.products.model.MovimientoStock;
import com.tpi_pais.mega_store.products.model.Producto;
import com.tpi_pais.mega_store.products.model.StockSucursal;
import com.tpi_pais.mega_store.products.model.Sucursal;
import com.tpi_pais.mega_store.products.repository.MovimientoStockRepository;
import com.tpi_pais.mega_store.products.repository.ProductoRepository;
import com.tpi_pais.mega_store.products.repository.StockSucursalRepository;
import com.tpi_pais.mega_store.products.repository.SucursalRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@Service
public class MovimientoStockService implements IMovimientoStockService {

    private final MovimientoStockRepository repository;
    private final IProductoService productoService;
    private final MovimientoStockMapper movimientoStockMapper;
    private final SucursalRepository sucursalRepository;
    private final StockSucursalRepository stockSucursalRepository;
    private final ProductoRepository productoRepository;

    public MovimientoStockService(
            MovimientoStockRepository repository,
            IProductoService productoService,
            MovimientoStockMapper movimientoStockMapper,
            SucursalRepository sucursalRepository,
            StockSucursalRepository stockSucursalRepository,
            ProductoRepository productoRepository) {
        this.repository = repository;
        this.productoService = productoService;
        this.movimientoStockMapper = movimientoStockMapper;
        this.sucursalRepository = sucursalRepository;
        this.productoRepository = productoRepository;
        this.stockSucursalRepository = stockSucursalRepository;
    }

    /*
    * Este guardar se utiliza cuando se hace un movimiento de stock.
    * Si es un egreso se hace un movimiento de stock de egreso,
    * si es un ingreso se hace un movimiento de stock de ingreso
    * */
    @Override
    public ArrayList<MovimientoStockDTO> guardar(MovimientoStockDTO model){
        Producto producto = productoService.buscarPorId(model.getIdProducto());

        Optional<Sucursal> sucursal = sucursalRepository.findByIdAndFechaEliminacionIsNull(model.getIdSucursal());
        ArrayList<MovimientoStockDTO> movimientoStock;
        if (model.getEsEgreso()){
            movimientoStock = this.egreso(producto, model.getCantidad());
        }else{
            movimientoStock = this.ingreso(producto, model.getCantidad(), sucursal.get());
        }

        return movimientoStock;
    };

    @Override
    public ArrayList<MovimientoStockDTO> ingreso (Producto producto, Integer cantidad, Sucursal sucursal){
        this.verificarCantidad(cantidad, false, producto);
        this.verificarSucursal(sucursal.getId());

        ArrayList<MovimientoStockDTO> movimientosDTO = new ArrayList<>();

        MovimientoStock movimientoStock = new MovimientoStock();
        movimientoStock.setProducto(producto);
        movimientoStock.setCantidad(cantidad);
        movimientoStock.setEsEgreso(false);
        movimientoStock.setSucursal(sucursal);
        MovimientoStock movimientoGuardado = repository.save(movimientoStock);

        producto.setStockActual(producto.getStockActual() + cantidad);
        productoRepository.save(producto);

        movimientosDTO.add(movimientoStockMapper.toDTO(movimientoGuardado));

        return movimientosDTO;
    }

    @Override
    public ArrayList<MovimientoStockDTO> egreso (Producto producto, Integer cantidad){
        //Falta la logica de descontar el stock
        this.verificarCantidad(cantidad, false, producto);

        ArrayList<StockSucursal> stockSucursales = stockSucursalRepository.findByProductoIdOrderByStockDesc(producto.getId());
        ArrayList<MovimientoStockDTO> movimientosDTO = new ArrayList<>();

        for (StockSucursal stockSucursal : stockSucursales){
            if (cantidad <= 0){
                break;
            }
            if (stockSucursal.getStock() >= cantidad){

                MovimientoStock movimientoStock = new MovimientoStock();
                movimientoStock.setProducto(producto);
                movimientoStock.setCantidad(cantidad);
                movimientoStock.setEsEgreso(true);
                movimientoStock.setSucursal(stockSucursal.getSucursal());
                MovimientoStock movimientoGuardado = repository.save(movimientoStock);

                stockSucursal.setStock(stockSucursal.getStock() - cantidad);
                stockSucursalRepository.save(stockSucursal);

                producto.setStockActual(producto.getStockActual() - cantidad);
                productoRepository.save(producto);

                movimientosDTO.add(movimientoStockMapper.toDTO(movimientoGuardado));

                break;
            }else{

                MovimientoStock movimientoStock = new MovimientoStock();
                movimientoStock.setProducto(producto);
                movimientoStock.setCantidad(stockSucursal.getStock());
                movimientoStock.setEsEgreso(true);
                movimientoStock.setSucursal(stockSucursal.getSucursal());
                MovimientoStock movimientoGuardado = repository.save(movimientoStock);

                stockSucursal.setStock(0);
                stockSucursalRepository.save(stockSucursal);

                producto.setStockActual(producto.getStockActual() - stockSucursal.getStock());
                productoRepository.save(producto);

                cantidad = cantidad - stockSucursal.getStock();

                movimientosDTO.add(movimientoStockMapper.toDTO(movimientoGuardado));
            }
        }

        return movimientosDTO;
    }


    public void verificarSucursal (Integer sucursalId){
        Optional<Sucursal> sucursal = sucursalRepository.findByIdAndFechaEliminacionIsNull(sucursalId);
        if (sucursal.isEmpty()){
            throw new BadRequestException(MessagesException.OBJECTO_INEXISTENTE);
        }
    }

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

    /*
    * Este guardar se usa unicamente cuando se crea un producto.
    * Dado un array de sucursales, se crean movimientos de stock para cada una de ellas
    * con un stock inicial de 0.
    * */
    @Override
    public void guardar (Producto producto, Integer[] sucursales){
        for (Integer sucursalId : sucursales) {
            MovimientoStock movimientoStock = new MovimientoStock();
            movimientoStock.setProducto(producto);
            movimientoStock.setCantidad(0);
            movimientoStock.setEsEgreso(false);
            Optional<Sucursal> sucursal = sucursalRepository.findByIdAndFechaEliminacionIsNull(sucursalId);
            if (sucursal.isEmpty()){
                throw new BadRequestException(MessagesException.OBJECTO_INEXISTENTE);
            }
            movimientoStock.setSucursal(sucursal.get());
            repository.save(movimientoStock);

            StockSucursal stockSucursal = new StockSucursal();
            stockSucursal.setStock(0);
            stockSucursal.setSucursal(sucursal.get());
            stockSucursal.setProducto(producto);
            stockSucursalRepository.save(stockSucursal);
        }
    }

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
        if (esEgreso != null){
            if (esEgreso){
                if (cantidad > producto.getStockActual()){
                    throw new BadRequestException(MessagesException.STOCK_INSUFICIENTE);
                }
            }
        }else {
            throw new BadRequestException("El campo esEgreso es obligatorio.");
        }
    };
}

