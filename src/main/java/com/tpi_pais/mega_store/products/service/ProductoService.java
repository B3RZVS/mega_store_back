package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.configs.ImageBBService;
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
import com.tpi_pais.mega_store.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Arrays;
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
    private final ImageBBService imgBBService;

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");


    public ProductoService(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository,
            SucursalRepository sucursalRepository,
            ColorRepository colorRepository,
            TalleRepository talleRepository,
            MarcaRepository marcaRepository,
            @Lazy MovimientoStockService movimientoStockService,
            ImageBBService imgBBService) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.sucursalRepository = sucursalRepository;
        this.colorRepository = colorRepository;
        this.talleRepository = talleRepository;
        this.marcaRepository = marcaRepository;
        this.movimientoStockService = movimientoStockService;
        this.imgBBService = imgBBService;
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
        modelDTO.setFoto(subirImagen(modelDTO.getImagen()));
        if (productoExistente(modelDTO.getNombre())) {
            Producto aux = buscarPorNombre(modelDTO.getNombre());
            if (aux.esEliminado()) {
                throw new BadRequestException(MessagesException.OBJECTO_ELIMINADO);
                //recuperar(aux); // Recuperar si el producto estaba eliminado
                //return ProductoMapper.toDTO(aux);
            } else {
                throw new BadRequestException(MessagesException.OBJETO_DUPLICADO);
            }
        }else {
            return guardar(modelDTO);
        }
    }

    public String subirImagen (MultipartFile imagen) {
        try {
            String imageUrl = imgBBService.subirImagen(imagen); // Usamos el servicio para subir la imagen
            if (imageUrl == null) {
                throw new BadRequestException("Error al subir la imagen.");
            }
            return imageUrl;
        } catch (Exception e) {
            throw new BadRequestException("Error al subir la imagen: " + e.getMessage());
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
        productoDTO.setNombre(StringUtils.capitalizeWords(productoDTO.getNombre()));
        verificarDescripcion(productoDTO.getDescripcion());
        verificarPrecio(productoDTO.getPrecio());
        verificarPeso(productoDTO.getPeso());
        verificarStock(productoDTO.getStockMedio(), productoDTO.getStockMinimo());
        verificarImagen(productoDTO.getImagen());
        verificarCategoria(productoDTO.getCategoriaId());
        verificarSucursal(productoDTO.getSucursalId());
        verificarColor(productoDTO.getColorId());
        verificarTalle(productoDTO.getTalleId());
        if (productoDTO.getStockActual() == null) {
            productoDTO.setStockActual(0); // Asigna el valor por defecto
        }
        return productoDTO;
    }

    @Override
    public void verificarNombre(ProductoDTO productoDTO) {
        String nombre = productoDTO.getNombre();
        if (nombre == null || nombre.isEmpty()) {
            throw new BadRequestException(MessagesException.CAMPO_NO_ENVIADO+"Nombre");
        }
        ExpresionesRegulares expReg = new ExpresionesRegulares();
        if (!expReg.verificarCaracteres(productoDTO.getNombre())){
            throw new BadRequestException(MessagesException.CARACTERES_INVALIDOS+"Nombre");
        }
        if (!expReg.verificarTextoConEspacios(productoDTO.getNombre())){
            productoDTO.setNombre(expReg.corregirCadena(productoDTO.getNombre()));
            if (Objects.equals(productoDTO.getNombre(), "")){
                throw new BadRequestException(MessagesException.FORMATO_INVALIDO+"Nombre");
            }
        }
    }

    @Override
    public void verificarDescripcion(String descripcion) {
        if (descripcion != null && descripcion.length() > 100) {
            throw new BadRequestException(MessagesException.LONGITUD_INVALIDA+"Descripcion");
        }
    }

    @Override
    public void verificarPrecio(BigDecimal precio) {
        if (precio == null || precio.compareTo(BigDecimal.valueOf(0.01)) < 0) {
            throw new BadRequestException(MessagesException.CAMPO_NUMERICO_MAYOR_0+"Precio");
        }
    }

    @Override
    public void verificarPeso(BigDecimal peso) {
        if (peso == null || peso.compareTo(BigDecimal.valueOf(0.01)) < 0) {
            throw new BadRequestException(MessagesException.CAMPO_NUMERICO_MAYOR_0+"Peso");
        }
    }

    @Override
    public void verificarStock(Integer stockMedio, Integer stockMinimo) {
        if (stockMinimo != null && stockMinimo <= 0) {
            throw new BadRequestException(MessagesException.CAMPO_NUMERICO_MAYOR_0+"Stock Minimo");
        }
        if (stockMedio != null && stockMedio <= 0) {
            throw new BadRequestException(MessagesException.CAMPO_NUMERICO_MAYOR_0+"Stock Medio");
        }
        if (stockMedio != null && stockMinimo != null && stockMedio <= stockMinimo) {
            throw new BadRequestException("El stock medio debe ser mayor que el stock mínimo.");
        }
    }

    @Override
    public void verificarCategoria(Integer categoriaId) {
        if (categoriaId == null) {
            throw new BadRequestException(MessagesException.CAMPO_NO_ENVIADO+"Categoria");
        }
        if (!categoriaRepository.existsById(categoriaId)) { // Verifica si existe la categoria con ese ID
            throw new BadRequestException(MessagesException.OBJECTO_INEXISTENTE+" Categoria");
        }
    }

    @Override
    public void verificarMarca(Integer marcaId) {
        if (marcaId == null) {
            throw new BadRequestException(MessagesException.CAMPO_NO_ENVIADO+"Marca");
        }
        if (!marcaRepository.existsById(marcaId)) { // Verifica si existe la marca con ese ID
            throw new BadRequestException(MessagesException.OBJECTO_INEXISTENTE+" Marca");
        }
    }

    @Override
    public void verificarSucursal(Integer sucursalId) {
        if (sucursalId == null) {
            throw new BadRequestException(MessagesException.CAMPO_NO_ENVIADO+"Sucursal");
        }
        if (!sucursalRepository.existsById(sucursalId)) { // Verifica si existe la sucursal con ese ID
            throw new BadRequestException(MessagesException.OBJECTO_INEXISTENTE+" Sucursal");
        }
    }

    @Override
    public void verificarColor(Integer colorId) {
        if (colorId == null) {
            throw new BadRequestException(MessagesException.CAMPO_NO_ENVIADO+"Color");
        }
        if (!colorRepository.existsById(colorId)) { // Verifica si existe el color con ese ID
            throw new BadRequestException(MessagesException.OBJECTO_INEXISTENTE+" Color");
        }
    }

    @Override
    public void verificarTalle(Integer talleId) {
        if (talleId == null) {
            throw new BadRequestException(MessagesException.CAMPO_NO_ENVIADO+"Talle");
        }
        if (!talleRepository.existsById(talleId)) { // Verifica si existe el talle con ese ID
            throw new BadRequestException(MessagesException.OBJECTO_INEXISTENTE+" Talle");
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
                throw new BadRequestException(MessagesException.STOCK_INSUFICIENTE);
            }
            producto.setStockActual(producto.getStockActual() - cantidad);
        }
        productoRepository.save(producto);
    }

    @Override
    public void verificarImagen(MultipartFile imagen) {
        if (imagen == null) {
            throw new BadRequestException(MessagesException.CAMPO_NO_ENVIADO+"Imagen");
        }
        if (imagen.isEmpty()) {
            throw new BadRequestException(MessagesException.CAMPO_NO_ENVIADO+"Imagen");
        }
        if (imagen.getSize() > MAX_FILE_SIZE) {
            throw new BadRequestException("El archivo de imagen es demasiado grande.");
        }
        // Verificar la extensión del archivo
        String fileName = imagen.getOriginalFilename();
        String fileExtension = fileName != null ? fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase() : "";

        if (!ALLOWED_EXTENSIONS.contains(fileExtension)) {
            throw new BadRequestException("Formato de archivo no permitido. Solo se permiten imágenes PNG y JPG.");
        }
    }

}
