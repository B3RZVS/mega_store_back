package com.tpi_pais.mega_store.products.controller.productoController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.ProductoDTO;
import com.tpi_pais.mega_store.products.model.*;
import com.tpi_pais.mega_store.products.repository.*;
import com.tpi_pais.mega_store.products.service.HistorialPrecioService;
import com.tpi_pais.mega_store.products.service.IProductoService;
import com.tpi_pais.mega_store.utils.ApiResponse;
import com.tpi_pais.mega_store.utils.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/products") // Define la ruta base para los endpoints de productos
public class PutProductoController {

    // Inyección de dependencias a través del constructor
    private final IProductoService productoService;
    private final CategoriaRepository categoriaRepository;
    private final SucursalRepository sucursalRepository;
    private final ColorRepository colorRepository;
    private final TalleRepository talleRepository;
    private final MarcaRepository marcaRepository;
    private final ResponseService responseService;
    private final HistorialPrecioService historialPrecioService;
    private final StringUtils stringUtils;

    // Constructor para la inyección de dependencias
    public PutProductoController(
            IProductoService productoService,
            CategoriaRepository categoriaRepository,
            SucursalRepository sucursalRepository,
            ColorRepository colorRepository,
            TalleRepository talleRepository,
            MarcaRepository marcaRepository,
            ResponseService responseService,
            HistorialPrecioService historialPrecioService,
            StringUtils stringUtils) {
        this.productoService = productoService;
        this.categoriaRepository = categoriaRepository;
        this.sucursalRepository = sucursalRepository;
        this.colorRepository = colorRepository;
        this.talleRepository = talleRepository;
        this.marcaRepository = marcaRepository;
        this.responseService = responseService;
        this.historialPrecioService = historialPrecioService;
        this.stringUtils = stringUtils;
    }

    // Endpoint PUT para actualizar un producto
    @PutMapping("/producto")
    public ResponseEntity<ApiResponse<Object>>  actualizar(
            @RequestBody ProductoDTO productoDTO,
            @RequestHeader("Authorization") String token
    ) {
        // Buscar el producto que se quiere modificar por su ID
        Producto productoModificar = productoService.buscarPorId(productoDTO.getId());
        token = stringUtils.limpiarToken(token);

        // Validar y actualizar solo los atributos que se incluyen en el request

        // Actualizar nombre si se proporciona
        if (productoDTO.getNombre() != null) {
            productoService.verificarNombre(productoDTO); // Verifica el nombre
            // Verificar si ya existe un producto con el mismo nombre, y si es diferente al actual
            if (productoService.productoExistente(productoDTO.getNombre())
                    && !productoModificar.getNombre().equals(productoDTO.getNombre())) {
                throw new BadRequestException("Ya existe un producto con ese nombre");
            }
            productoModificar.setNombre(productoDTO.getNombre());
        }

        // Actualizar descripción si se proporciona
        if (productoDTO.getDescripcion() != null) {
            productoService.verificarDescripcion(productoDTO.getDescripcion()); // Verifica la descripción
            productoModificar.setDescripcion(productoDTO.getDescripcion());
        }

        // Actualizar precio si se proporciona
        if (productoDTO.getPrecio() != null && productoDTO.getPrecio() != productoModificar.getPrecio()) {
            productoService.actualizarPrecio(productoModificar, productoDTO.getPrecio(), token);
            productoModificar.setPrecio(productoDTO.getPrecio());
        }

        // Actualizar peso si se proporciona
        if (productoDTO.getPeso() != null) {
            productoService.verificarPeso(productoDTO.getPeso()); // Verifica el peso
            productoModificar.setPeso(productoDTO.getPeso());
        }

        // Actualizar stock mínimo si se proporciona
        if (productoDTO.getStockMinimo() != null) {
            productoService.verificarStock(productoDTO.getStockMedio(), productoDTO.getStockMinimo()); // Verifica stock
            productoModificar.setStockMinimo(productoDTO.getStockMinimo());
        }

        // Actualizar categoría si se proporciona
        if (productoDTO.getCategoriaId() != null) {
            productoService.verificarCategoria(productoDTO.getCategoriaId()); // Verifica la categoría
            Categoria categoria = categoriaRepository.findById(productoDTO.getCategoriaId())
                    .orElseThrow(() -> new BadRequestException("La categoría especificada no existe."));
            productoModificar.setCategoria(categoria);
        }

        // Actualizar talle si se proporciona
        if (productoDTO.getTalleId() != null) {
            productoService.verificarTalle(productoDTO.getTalleId()); // Verifica el talle
            Talle talle = talleRepository.findById(productoDTO.getTalleId())
                    .orElseThrow(() -> new BadRequestException("El talle especificado no existe."));
            productoModificar.setTalle(talle);
        }

        // Actualizar color si se proporciona
        if (productoDTO.getColorId() != null) {
            productoService.verificarColor(productoDTO.getColorId()); // Verifica el color
            Color color = colorRepository.findById(productoDTO.getColorId())
                    .orElseThrow(() -> new BadRequestException("El color especificado no existe."));
            productoModificar.setColor(color);
        }

        // Actualizar marca si se proporciona
        if (productoDTO.getMarcaId() != null) {
            productoService.verificarMarca(productoDTO.getMarcaId()); // Verifica la marca
            Marca marca = marcaRepository.findById(productoDTO.getMarcaId())
                    .orElseThrow(() -> new BadRequestException("La marca especificada no existe."));
            productoModificar.setMarca(marca);
        }

        // Actualizar foto si se proporciona (descomentado en el código original)
        if (productoDTO.getFoto() != null) {
            //productoService.verificarFoto(productoDTO.getFoto()); // Descomentado, podría verificar la foto
            productoModificar.setFoto(productoDTO.getFoto());
        }

        // Guardar y retornar el producto actualizado
        Producto productoGuardado = productoService.actualizar(productoModificar);

        // Retornar la respuesta de éxito
        return responseService.successResponse(productoGuardado, "Producto actualizado");
    }

    // Endpoint PUT para recuperar un producto eliminado
    @PutMapping("/producto/recuperar/{id}")
    public ResponseEntity<ApiResponse<Object>>  recuperar(@PathVariable Integer id) {
        // Buscar el producto eliminado por ID
        Producto producto = productoService.buscarEliminadoPorId(id);

        // Recuperar el producto si estaba eliminado
        productoService.recuperar(producto);
        // Retornar la respuesta de éxito
        return responseService.successResponse(producto, "Producto recuperado");
    }
}
