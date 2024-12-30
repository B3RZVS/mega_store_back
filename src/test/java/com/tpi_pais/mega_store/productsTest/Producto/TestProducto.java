package com.tpi_pais.mega_store.productsTest.Producto;

import com.tpi_pais.mega_store.products.dto.ProductoDTO;
import com.tpi_pais.mega_store.products.model.Producto;
import com.tpi_pais.mega_store.products.repository.ProductoRepository;
import com.tpi_pais.mega_store.products.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional  // Esto asegura que los cambios en la base de datos se reviertan después de la prueba
public class TestProducto {

    @InjectMocks
    private ProductoService productoService;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ProductoDTO productoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Crear un ProductoDTO con datos válidos para la prueba
        productoDTO = new ProductoDTO();
        productoDTO.setNombre("Producto de prueba");
        productoDTO.setDescripcion("Descripción del producto");
        productoDTO.setPrecio(BigDecimal.valueOf(100.0));
        productoDTO.setPeso(BigDecimal.valueOf(500.0));
        productoDTO.setFoto("foto.jpg");
        productoDTO.setStockMedio(20);
        productoDTO.setStockMinimo(5);
        productoDTO.setCategoriaId(1);
        productoDTO.setSucursalId(1);
        productoDTO.setMarcaId(1);
        productoDTO.setTalleId(1);
        productoDTO.setColorId(1);
    }

    @Test
    public void testGuardarProductoEnBaseDeDatos() {
        // When: Llamamos al servicio para guardar el producto

        ProductoDTO productoGuardadoDTO = productoService.guardar(productoDTO);

        // Then: Verificamos que el producto ha sido guardado correctamente en la base de datos
        Optional<Producto> productoGuardado = productoRepository.findByNombreAndFechaEliminacionIsNull("Producto de prueba");

        // Verificamos si el producto fue guardado correctamente
        assertTrue(productoGuardado.isPresent(), "El producto no fue guardado correctamente");

        // Si el producto está presente, verificamos sus propiedades
        Producto producto = productoGuardado.get();

        assertEquals("Producto de prueba", producto.getNombre());
        assertEquals("Descripción del producto", producto.getDescripcion());
        assertEquals(BigDecimal.valueOf(100.0), producto.getPrecio());
        assertEquals(BigDecimal.valueOf(500.0), producto.getPeso());
        assertEquals("foto.jpg", producto.getFoto());
        assertEquals(20, producto.getStockMedio());
        assertEquals(5, producto.getStockMinimo());
        assertEquals(1, producto.getCategoria().getId());
        assertEquals(1, producto.getSucursal().getId());
        assertEquals(1, producto.getMarca().getId());
        assertEquals(1, producto.getTalle().getId());
        assertEquals(1, producto.getColor().getId());
    }
}
