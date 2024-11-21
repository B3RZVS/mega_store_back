package com.tpi_pais.mega_store.products.controller.productoController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.MarcaDTO;
import com.tpi_pais.mega_store.products.dto.ProductoDTO;
import com.tpi_pais.mega_store.products.model.*;
import com.tpi_pais.mega_store.products.repository.*;
import com.tpi_pais.mega_store.products.service.HistorialPrecioService;
import com.tpi_pais.mega_store.products.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PutProductoController {

    @Autowired
    private IProductoService productoService;

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private SucursalRepository sucursalRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private TalleRepository talleRepository;
    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ResponseService responseService;

    @PutMapping("/producto")
    public ResponseEntity<?> actualizar(@RequestBody ProductoDTO productoDTO, @RequestHeader("Authorization") String token) {
        // Buscar el producto que se quiere modificar por su ID
        Producto productoModificar = productoService.buscarPorId(productoDTO.getId());


        // Validar y actualizar solo los atributos que se incluyen en el request
        if (productoDTO.getNombre() != null) {
            productoService.verificarNombre(productoDTO);
            // Validar si el nombre del producto ya existe y es diferente del actual
            if (productoService.productoExistente(productoDTO.getNombre())
                    && !productoModificar.getNombre().equals(productoDTO.getNombre())) {
                throw new BadRequestException("Ya existe un producto con ese nombre");
            }
            productoModificar.setNombre(productoDTO.getNombre());
        }

        if (productoDTO.getDescripcion() != null) {
            productoService.verificarDescripcion(productoDTO.getDescripcion());
            productoModificar.setDescripcion(productoDTO.getDescripcion());
        }

        if (productoDTO.getPrecio() != null) {
            productoService.verificarPrecio(productoDTO.getPrecio());
            productoModificar.setPrecio(productoDTO.getPrecio());
            HistorialPrecioService historialPrecioService = new HistorialPrecioService();
            historialPrecioService.crear(productoDTO.getPrecio().doubleValue(), productoModificar, token);
        }

        if (productoDTO.getPeso() != null) {
            productoService.verificarPeso(productoDTO.getPeso());
            productoModificar.setPeso(productoDTO.getPeso());
        }

        if (productoDTO.getStockMinimo() != null) {
            productoService.verificarStock(productoDTO.getStockMedio(), productoDTO.getStockMinimo());
            productoModificar.setStockMinimo(productoDTO.getStockMinimo());
        }

        if (productoDTO.getStockActual() != null) {
            productoModificar.setStockActual(productoDTO.getStockActual());
        }

        if (productoDTO.getCategoriaId() != null) {
            productoService.verificarCategoria(productoDTO.getCategoriaId());
            Categoria categoria = categoriaRepository.findById(productoDTO.getCategoriaId())
                    .orElseThrow(() -> new BadRequestException("La categoría especificada no existe."));
            productoModificar.setCategoria(categoria);
        }

        if (productoDTO.getSucursalId() != null) {
            productoService.verificarSucursal(productoDTO.getSucursalId());
            Sucursal sucursal = sucursalRepository.findById(productoDTO.getSucursalId())
                    .orElseThrow(() -> new BadRequestException("La sucursal especificada no existe."));
            productoModificar.setSucursal(sucursal);
        }

        if (productoDTO.getTalleId() != null) {
            productoService.verificarTalle(productoDTO.getTalleId());
            Talle talle = talleRepository.findById(productoDTO.getTalleId())
                    .orElseThrow(() -> new BadRequestException("El talle especificado no existe."));
            productoModificar.setTalle(talle);
        }

        if (productoDTO.getColorId() != null) {
            productoService.verificarColor(productoDTO.getColorId());
            Color color = colorRepository.findById(productoDTO.getColorId())
                    .orElseThrow(() -> new BadRequestException("El color especificado no existe."));
            productoModificar.setColor(color);
        }
        if (productoDTO.getMarcaId() != null) {
            productoService.verificarMarca(productoDTO.getMarcaId());
            Marca marca = marcaRepository.findById(productoDTO.getMarcaId())
                    .orElseThrow(() -> new BadRequestException("La marca especificada no existe."));
            productoModificar.setMarca(marca);
        }

        if (productoDTO.getFoto() != null) {
            productoService.verificarFoto(productoDTO.getFoto());
            productoModificar.setFoto(productoDTO.getFoto());
        }

        // Guardar y retornar el producto actualizado
        Producto productoGuardado = productoService.guardar(productoModificar);

        return responseService.successResponse(productoGuardado, "Producto actualizado");
    }

    @PutMapping("/producto/recuperar/{id}")
    public ResponseEntity<?> recuperar(@PathVariable Integer id) {
        // Buscar el producto eliminado por ID
        Producto producto = productoService.buscarEliminadoPorId(id);

        // Recuperar el producto si estaba eliminado
        productoService.recuperar(producto);
        return responseService.successResponse(producto, "Producto recuperado");
    }
}
