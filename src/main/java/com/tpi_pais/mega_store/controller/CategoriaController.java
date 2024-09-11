package com.tpi_pais.mega_store.controller;

import com.tpi_pais.mega_store.dto.CategoriaDTO;
import com.tpi_pais.mega_store.mapper.CategoriaMapper;
import com.tpi_pais.mega_store.exception.RecursoNoEncontradoExcepcion;
import com.tpi_pais.mega_store.model.Categoria;
import com.tpi_pais.mega_store.service.ICategoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mega_store/backend")
@CrossOrigin(value=" http://localhost:8080")
public class CategoriaController {
    private static final Logger logger = LoggerFactory.getLogger(CategoriaController.class);
    @Autowired
    private ICategoriaService modelService;

    @GetMapping({"/categorias"})
    public List<CategoriaDTO> getAll() {
        logger.info("Entra y trae todas las categoria");
        return modelService.listar();

    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<CategoriaDTO> getPorId(@PathVariable Integer id){
        logger.info("Buscando categoría con ID: {}", id);
        Categoria model = modelService.buscarPorId(id);

        if(model == null){
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
        CategoriaDTO modelDTO = CategoriaMapper.toDTO(model);
        return ResponseEntity.ok(modelDTO);
    }


    
    @PostMapping("/categoria")
    public CategoriaDTO guardar(@RequestBody CategoriaDTO model){
        return modelService.guardar(model);
    }

    @PutMapping("/categoria")
    public CategoriaDTO actualizar(@RequestBody CategoriaDTO model){

        return modelService.guardar(model);
    }

    @DeleteMapping("/catetoria/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        logger.info("Eliminando categoría con ID: {}", id);
        Categoria model = modelService.buscarPorId(id);
        if (model == null){
            logger.warn("Categoría con ID {} no encontrada", id);
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        }else if (model.getFechaEliminacion() != null) {
            throw new RecursoNoEncontradoExcepcion("No se puede recuperar debido a que el recurso se encuentra eliminado.");
        }

        model.eliminar();
        modelService.eliminar(model);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/categoria/recuperar/{id}")
    public ResponseEntity<Void> recuperar(@PathVariable Integer id) {
        System.out.println(id);
        Categoria model = modelService.buscarPorId(id);
        if (model == null) {
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        }else if (model.getFechaEliminacion() == null) {
            throw new RecursoNoEncontradoExcepcion("No se puede recuperar debido a que el recurso no esta eliminado.");
        }
    
        model.recuperar();  // Método que pondrá el campo `fechaEliminacion` en null
        modelService.guardar(model);
        return ResponseEntity.ok().build();
    }
}
