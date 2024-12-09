package com.tpi_pais.mega_store.products.controller.productoController;

import com.tpi_pais.mega_store.configs.ImageBBService;
import com.tpi_pais.mega_store.configs.SessionRequired;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.ProductoDTO; // Asegúrate de tener este DTO
import com.tpi_pais.mega_store.products.service.IProductoService; // Servicio de Producto
import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@CrossOrigin(origins = "http://localhost:5173") // Permite solicitudes desde un dominio específico (localhost:5173)
@RestController
@RequestMapping("/products") // Define la ruta base para los productos
public class PostProductoController {

    // Inyección de dependencias a través del constructor
    private final ImageBBService imgBBService;
    private final IProductoService productoService; // Servicio para manejar productos
    private final ResponseService responseService; // Servicio para manejar respuestas

    public PostProductoController(ImageBBService imgBBService, IProductoService productoService, ResponseService responseService) {
        this.imgBBService = imgBBService;
        this.productoService = productoService;
        this.responseService = responseService;
    }

    // Endpoint para crear un nuevo producto
    @PostMapping("/producto")
    public ResponseEntity<ApiResponse<Object>> guardar(
            @RequestParam("nombre") String nombre, // Parámetros de la solicitud
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") String precio,
            @RequestParam("peso") String peso,
            @RequestParam("stockMedio") String stockMedio,
            @RequestParam("stockMinimo") String stockMinimo,
            @RequestParam("categoriaId") String categoriaId,
            @RequestParam("sucursalId") String sucursalId,
            @RequestParam("marcaId") String marcaId,
            @RequestParam("talleId") String talleId,
            @RequestParam("colorId") String colorId,
            @RequestPart("imagen") MultipartFile imagen // Para subir una imagen
    ) {

        // Crear un objeto ProductoDTO y establecer sus valores
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombre(nombre);
        productoDTO.setDescripcion(descripcion);

        // Subir la imagen a ImageBB
        try {
            String imageUrl = imgBBService.subirImagen(imagen); // Usamos el servicio para subir la imagen
            if (imageUrl == null) {
                throw new BadRequestException("Error al subir la imagen.");
            }
            productoDTO.setFoto(imageUrl); // Establecemos la URL de la imagen
        } catch (Exception e) {
            throw new BadRequestException("Error al subir la imagen: " + e.getMessage());
        }

        // Establecer valores numéricos con validación
        try {
            productoDTO.setPrecio(new BigDecimal(precio)); // Convertir precio a BigDecimal
            productoDTO.setPeso(new BigDecimal(peso)); // Convertir peso a BigDecimal
            productoDTO.setStockActual(0); // Inicializamos el stock actual en 0
            productoDTO.setStockMedio(Integer.parseInt(stockMedio)); // Convertir stock medio a entero
            productoDTO.setStockMinimo(Integer.parseInt(stockMinimo)); // Convertir stock mínimo a entero
        } catch (NumberFormatException e) {
            throw new BadRequestException("El precio, peso, stockMedio y stockMinimo deben ser numéricos.");
        }

        // Parsear los IDs de las relaciones foráneas (categoría, sucursal, etc.)
        productoDTO.setCategoriaId(Integer.parseInt(categoriaId));
        productoDTO.setSucursalId(Integer.parseInt(sucursalId));
        productoDTO.setMarcaId(Integer.parseInt(marcaId));
        productoDTO.setTalleId(Integer.parseInt(talleId));
        productoDTO.setColorId(Integer.parseInt(colorId));

        // Llamar al servicio para crear el producto y devolver la respuesta
        return responseService.successResponse(
                productoService.crear(productoDTO), // Crear el producto utilizando el servicio
                "Producto creado exitosamente" // Mensaje de éxito
        );
    }

    // Endpoint adicional para subir una imagen
    @PostMapping("/imagen")
    public ResponseEntity<ApiResponse<Object>> guardarImagen(@RequestPart("foto") MultipartFile foto) throws BadRequestException {
        try {
            // Subir la imagen y obtener la URL
            String imageUrl = imgBBService.subirImagen(foto);
            // Retornar la respuesta con la URL de la imagen
            return responseService.successResponse(imageUrl, "Imagen subida exitosamente");
        } catch (Exception e) {
            // Si ocurre un error, devolver el mensaje de error
            return responseService.successResponse(e.getMessage());
        }
    }
}

