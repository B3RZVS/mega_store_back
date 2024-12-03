package com.tpi_pais.mega_store.products.controller.productoController;

import com.tpi_pais.mega_store.configs.ImageBBService;
import com.tpi_pais.mega_store.configs.SessionRequired;
import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.ProductoDTO; // Asegúrate de tener este DTO
import com.tpi_pais.mega_store.products.service.IProductoService; // Servicio de Producto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/products")
public class PostProductoController {

    private final ImageBBService imgBBService;
    private final IProductoService productoService; // Servicio para manejar productos
    private final ResponseService responseService; // Servicio para respuestas

    public PostProductoController(ImageBBService imgBBService, IProductoService productoService, ResponseService responseService) {
        this.imgBBService = imgBBService;
        this.productoService = productoService;
        this.responseService = responseService;
    }



    @PostMapping("/producto")
    public ResponseEntity<?> guardar(
            @RequestParam("nombre") String nombre,
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
            @RequestPart("imagen") MultipartFile imagen
    ) {

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombre(nombre);
        productoDTO.setDescripcion(descripcion);
        // Subir imagen
        try {
            String imageUrl = imgBBService.subirImagen(imagen);
            if (imageUrl == null) {
                throw new BadRequestException("Error al subir la imagen.");
            }
            productoDTO.setFoto(imageUrl);
        } catch (Exception e) {
            throw new BadRequestException("Error al subir la imagen: " + e.getMessage());
        }
        try {
            productoDTO.setPrecio(new BigDecimal(precio));
            productoDTO.setPeso(new BigDecimal(peso));
            productoDTO.setStockActual(0);
            productoDTO.setStockMedio(Integer.parseInt(stockMedio));
            productoDTO.setStockMinimo(Integer.parseInt(stockMinimo));
        } catch (NumberFormatException e) {
            throw new BadRequestException("El precio, peso, stockMedio y stockMinimo deben ser numéricos.");
        }
        // Parsear IDs de relaciones foráneas
        productoDTO.setCategoriaId(Integer.parseInt(categoriaId));
        productoDTO.setSucursalId(Integer.parseInt(sucursalId));
        productoDTO.setMarcaId(Integer.parseInt(marcaId));
        productoDTO.setTalleId(Integer.parseInt(talleId));
        productoDTO.setColorId(Integer.parseInt(colorId));

        // Llamar al servicio y devolver respuesta
        return responseService.successResponse(
                productoService.crear(productoDTO),
                "Producto creado exitosamente"
        );
    }
    @PostMapping("/imagen")
    public ResponseEntity<?> guardarImagen(@RequestPart("foto") MultipartFile foto) throws Exception {
        try {
            String imageUrl = imgBBService.subirImagen(foto);
            return responseService.successResponse(imageUrl,"Imagen subida exitosamente");
        }catch (Exception e){
            return responseService.successResponse(e.getMessage());
        }
    }

}
