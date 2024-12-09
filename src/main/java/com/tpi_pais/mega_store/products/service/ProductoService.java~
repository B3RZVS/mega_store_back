package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.exception.MessagesException;
import com.tpi_pais.mega_store.products.dto.MovimientoStockDTO;
import com.tpi_pais.mega_store.products.mapper.MarcaMapper;
import com.tpi_pais.mega_store.products.model.Marca;
import com.tpi_pais.mega_store.products.model.Producto;
import com.tpi_pais.mega_store.products.dto.ProductoDTO;
import com.tpi_pais.mega_store.products.repository.*;
import com.tpi_pais.mega_store.exception.NotFoundException;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.products.mapper.ProductoMapper;
import com.tpi_pais.mega_store.utils.ExpresionesRegulares;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final SucursalRepository sucursalRepository;
    private final ColorRepository colorRepository;
    private final TalleRepository talleRepository;
    private final MarcaRepository marcaRepository;
    private final MovimientoStockService movimientoStockService;

    public ProductoService(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository,
            SucursalRepository sucursalRepository,
            ColorRepository colorRepository,
            TalleRepository talleRepository,
            MarcaRepository marcaRepository,
            @Lazy MovimientoStockService movimientoStockService) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.sucursalRepository = sucursalRepository;
        this.colorRepository = colorRepository;
        this.talleRepository = talleRepository;
        this.marcaRepository = marcaRepository;
        this.movimientoStockService = movimientoStockService;
    }





    @Override
    public List<ProductoDTO> listar() {
        List<Producto> productos = productoRepository.findByFechaEliminacionIsNullOrderByIdAsc();
        return productos.stream().map(ProductoMapper::toDTO).toList();
    }

    @Override
    public Producto buscarPorId(Integer id) {
        return productoRepository.findByIdAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new NotFoundException(MessagesException.OBJECTO_NO_ENCONTRADO));
    }

    @Override
    public Producto buscarPorNombre(String nombre) {
        return productoRepository.findByNombre(nombre).orElse(null);
    }

    @Override
    public Producto buscarEliminadoPorId(Integer id) {
        return productoRepository.findByIdAndFechaEliminacionIsNotNull(id)
                .orElseThrow(() -> new NotFoundException(MessagesException.OBJECTO_NO_ENCONTRADO));
    }

    @Override
    public ProductoDTO crear(ProductoDTO modelDTO) {
        modelDTO = this.verificarAtributos(modelDTO);
        if (productoExistente(modelDTO.getNombre())) {
            Producto aux = buscarPorNombre(modelDTO.getNombre());
            if (aux.esEliminado()) {
                throw new BadRequestException("El producto ya estaba eliminado");
                //recuperar(aux); // Recuperar si el producto estaba eliminado
                //return ProductoMapper.toDTO(aux);
            } else {
                throw new BadRequestException("Ya existe un producto con ese nombre");
            }
        }else {
            return guardar(modelDTO);
        }
    }

    @Override
    public ProductoDTO guardar(ProductoDTO modelDTO) {
        Producto model = new Producto();
        model.setNombre(modelDTO.getNombre());
        model.setDescripcion(modelDTO.getDescripcion());
        model.setPrecio(modelDTO.getPrecio());
        model.setPeso(modelDTO.getPeso());
        model.setStockActual(modelDTO.getStockActual());
        model.setStockMinimo(modelDTO.getStockMinimo());
        model.setStockMedio(modelDTO.getStockMedio());
        model.setFoto(modelDTO.getFoto());
        model.setCategoria(categoriaRepository.findById(modelDTO.getCategoriaId()).orElse(null));
        model.setSucursal(sucursalRepository.findById(modelDTO.getSucursalId()).orElse(null));
        model.setMarca(marcaRepository.findById(modelDTO.getMarcaId()).orElse(null));
        model.setTalle(talleRepository.findById(modelDTO.getTalleId()).orElse(null));
        model.setColor(colorRepository.findById(modelDTO.getColorId()).orElse(null));
        ProductoDTO productoDTO = ProductoMapper.toDTO(productoRepository.save(model));
        MovimientoStockDTO movimientoStockDTO = movimientoStockService.guardar(productoDTO.getId(), productoDTO.getStockActual(), false);
        return productoDTO;
    }

    @Override
    public Producto guardar(Producto producto) {
        Producto productoGuardado = productoRepository.save(producto);
        MovimientoStockDTO movimientoStockDTO = movimientoStockService.guardar(productoGuardado.getId(), productoGuardado.getStockActual(), false);
        return productoGuardado;
    }

    @Override
    public void eliminar(Producto producto, String usuario) {
        producto.eliminar(usuario);
        productoRepository.save(producto);
    }

    @Override
    public void recuperar(Producto producto) {
        producto.recuperar();
        productoRepository.save(producto);
    }

    @Override
    public ProductoDTO verificarAtributos(ProductoDTO productoDTO) {
        verificarNombre(productoDTO);
        verificarDescripcion(productoDTO.getDescripcion());
        verificarPrecio(productoDTO.getPrecio());
        verificarPeso(productoDTO.getPeso());
        verificarStock(productoDTO.getStockMedio(), productoDTO.getStockMinimo());
        //verificarFoto(productoDTO.getFoto());
        verificarCategoria(productoDTO.getCategoriaId());
        verificarSucursal(productoDTO.getSucursalId());
        verificarColor(productoDTO.getColorId());
        verificarTalle(productoDTO.getTalleId());
        if (productoDTO.getStockActual() == null) {
            productoDTO.setStockActual(0); // Asigna el valor por defecto
        }
        return productoDTO;
    }

    public void verificarNombre(ProductoDTO productoDTO) {
        String nombre = productoDTO.getNombre();
        if (nombre == null || nombre.isEmpty()) {
            throw new BadRequestException("El nombre es obligatorio.");
        }
        ExpresionesRegulares expReg = new ExpresionesRegulares();
        if (!expReg.verificarCaracteres(productoDTO.getNombre())){
            throw new BadRequestException("El nombre enviado contiene caracteres no permitidos.");
        }
        if (!expReg.verificarTextoConEspacios(productoDTO.getNombre())){
            productoDTO.setNombre(expReg.corregirCadena(productoDTO.getNombre()));
            if (Objects.equals(productoDTO.getNombre(), "")){
                throw new BadRequestException("El nombre tiene un formato incorrecto");
            }
        }
        productoDTO.capitalizarNombre();
    }

    public void verificarDescripcion(String descripcion) {
        if (descripcion != null && descripcion.length() > 100) {
            throw new BadRequestException("La descripción no debe exceder los 100 caracteres.");
        }
    }

    public void verificarPrecio(BigDecimal precio) {
        if (precio == null || precio.compareTo(BigDecimal.valueOf(0.01)) < 0) {
            throw new BadRequestException("El precio debe ser mayor que 0.");
        }
    }

    public void verificarPeso(BigDecimal peso) {
        if (peso == null || peso.compareTo(BigDecimal.valueOf(0.01)) < 0) {
            throw new BadRequestException("El peso debe ser mayor que 0.");
        }
    }

    public void verificarStock(Integer stockMedio, Integer stockMinimo) {
        if (stockMinimo != null && stockMinimo < 0) {
            throw new BadRequestException("El stock mínimo debe ser mayor o igual a 0.");
        }
        if (stockMedio != null && stockMedio <= 0) {
            throw new BadRequestException("El stock medio debe ser mayor que 0.");
        }
        if (stockMedio != null && stockMinimo != null && stockMedio <= stockMinimo) {
            throw new BadRequestException("El stock medio debe ser mayor que el stock mínimo.");
        }
    }



    public void verificarFoto(String nombreFoto) {
        if (!validarFoto(nombreFoto)) {
            throw new BadRequestException("La foto debe ser .jpg o .png y no debe exceder los 5 MB.");
        }
    }

    public void verificarCategoria(Integer categoriaId) {
        if (categoriaId == null) {
            throw new BadRequestException("El ID de la categoría es obligatorio.");
        }
        if (!categoriaRepository.existsById(categoriaId)) { // Verifica si existe la categoria con ese ID
            throw new BadRequestException("La categoría especificada no existe.");
        }
    }
    public void verificarMarca(Integer marcaId) {
        if (marcaId == null) {
            throw new BadRequestException("El ID de la marca es obligatorio.");
        }
        if (!marcaRepository.existsById(marcaId)) { // Verifica si existe la marca con ese ID
            throw new BadRequestException("La marca especificada no existe.");
        }
    }

    public void verificarSucursal(Integer sucursalId) {
        if (sucursalId == null) {
            throw new BadRequestException("El ID de la sucursal es obligatorio.");
        }
        if (!sucursalRepository.existsById(sucursalId)) { // Verifica si existe la sucursal con ese ID
            throw new BadRequestException("La sucursal especificada no existe.");
        }
    }

    public void verificarColor(Integer colorId) {
        if (colorId == null) {
            throw new BadRequestException("El ID del color es obligatorio.");
        }
        if (!colorRepository.existsById(colorId)) { // Verifica si existe el color con ese ID
            throw new BadRequestException("El color especificado no existe.");
        }
    }

    public void verificarTalle(Integer talleId) {
        if (talleId == null) {
            throw new BadRequestException("El ID del talle es obligatorio.");
        }
        if (!talleRepository.existsById(talleId)) { // Verifica si existe el talle con ese ID
            throw new BadRequestException("El talle especificado no existe.");
        }
    }

    @Override
    public boolean productoExistente(String nombre) {
        return productoRepository.findByNombre(nombre).isPresent();
    }

    @Override
    public void actualizarStock(Producto producto, int cantidad, boolean esEntrada) {
        if (esEntrada) {
            producto.setStockActual(producto.getStockActual() + cantidad);
        } else {
            if (producto.getStockActual() < cantidad) {
                throw new BadRequestException("No hay suficiente stock para realizar la salida.");
            }
            producto.setStockActual(producto.getStockActual() - cantidad);
        }
        productoRepository.save(producto);
    }

    private boolean validarFoto(String nombreFoto) {
        return nombreFoto != null && (nombreFoto.endsWith(".jpg") || nombreFoto.endsWith(".png"));
    }

}
