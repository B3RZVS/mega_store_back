package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.products.mapper.MarcaMapper;
import com.tpi_pais.mega_store.products.model.Marca;
import com.tpi_pais.mega_store.products.model.Producto;
import com.tpi_pais.mega_store.products.dto.ProductoDTO;
import com.tpi_pais.mega_store.products.repository.ProductoRepository;
import com.tpi_pais.mega_store.exception.NotFoundException;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.products.mapper.ProductoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<ProductoDTO> listar() {
        List<Producto> productos = productoRepository.findByFechaEliminacionIsNullOrderByIdAsc();
        return productos.stream().map(ProductoMapper::toDTO).toList();
    }

    @Override
    public Producto buscarPorId(Integer id) {
        return productoRepository.findByIdAndFechaEliminacionIsNull(id)
                .orElseThrow(() -> new NotFoundException("El producto con id " + id + " no existe o está eliminado."));
    }

    @Override
    public Producto buscarPorNombre(String nombre) {
        return productoRepository.findByNombre(nombre).orElse(null);
    }

    @Override
    public Producto buscarEliminadoPorId(Integer id) {
        return productoRepository.findByIdAndFechaEliminacionIsNotNull(id)
                .orElseThrow(() -> new NotFoundException("El producto con id " + id + " no existe o no está eliminado."));
    }

    @Override
    public ProductoDTO guardar(ProductoDTO modelDTO) {
        Producto model = ProductoMapper.toEntity(modelDTO);
        return ProductoMapper.toDTO(productoRepository.save(model));
    }

    @Override
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
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
        if (productoDTO.getNombre() == null || productoDTO.getNombre().isEmpty()) {
            throw new BadRequestException("El nombre es obligatorio.");
        }
        if (productoDTO.getPrecio() == null || productoDTO.getPrecio().compareTo(BigDecimal.valueOf(0.01)) < 0) {
            throw new BadRequestException("El precio debe ser mayor que 0.");
        }
        if (productoDTO.getPeso() == null || productoDTO.getPeso().compareTo(BigDecimal.valueOf(0.01)) < 0) {
            throw new BadRequestException("El peso debe ser mayor que 0.");
        }
        if (productoDTO.getStockMedio() != null && productoDTO.getStockMedio() <= productoDTO.getStockMinimo()) {
            throw new BadRequestException("El stock medio debe ser mayor que el stock mínimo.");
        }
        if (!validarFoto(productoDTO.getFoto())) {
            throw new BadRequestException("La foto debe ser .jpg o .png y no debe exceder los 5 MB.");
        }
        return productoDTO;
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

    @Override
    public ProductoDTO actualizarProducto(ProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(productoDTO.getId())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        // Actualizar solo los campos que están presentes en el DTO
        if (productoDTO.getNombre() != null && !productoDTO.getNombre().isEmpty()) {
            producto.setNombre(productoDTO.getNombre());
        }

        if (productoDTO.getDescripcion() != null && !productoDTO.getDescripcion().isEmpty()) {
            producto.setDescripcion(productoDTO.getDescripcion());
        }

        if (productoDTO.getPrecio() != null) {
            producto.setPrecio(productoDTO.getPrecio());
        }

        if (productoDTO.getStockActual() != null) {
            producto.setStockActual(productoDTO.getStockActual());
        }

        if (productoDTO.getFoto() != null && !productoDTO.getFoto().isEmpty()) {
            producto.setFoto(productoDTO.getFoto());
        }

        if (productoDTO.getPeso() != null) {
            producto.setPeso(productoDTO.getPeso());
        }

        if (productoDTO.getStockMedio() != null) {
            producto.setStockMedio(productoDTO.getStockMedio());
        }

        if (productoDTO.getStockMinimo() != null) {
            producto.setStockMinimo(productoDTO.getStockMinimo());
        }

        if (productoDTO.getCategoriaId() != null) {
            producto.setCategoriaId(productoDTO.getCategoriaId());
        }

        if (productoDTO.getSucursalId() != null) {
            producto.setSucursalId(productoDTO.getSucursalId());
        }

        if (productoDTO.getMarcaId() != null) {
            producto.setMarcaId(productoDTO.getMarcaId());
        }

        if (productoDTO.getTalleId() != null) {
            producto.setTalleId(productoDTO.getTalleId());
        }

        if (productoDTO.getColorId() != null) {
            producto.setColorId(productoDTO.getColorId());
        }

        // Guardar el producto actualizado
        productoRepository.save(producto);

        return ProductoMapper.toDTO(producto);
    }
}
