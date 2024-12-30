package com.tpi_pais.mega_store.products.repository;

import com.tpi_pais.mega_store.products.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interfaz DetalleVentaRepository
 * Esta interfaz define los métodos necesarios para realizar operaciones CRUD
 * sobre la entidad DetalleVenta en la base de datos. Extiende JpaRepository,
 * proporcionando funcionalidades básicas como guardar, actualizar, eliminar y buscar.
 * Además, incluye un método personalizado para obtener los detalles de una venta
 * específica según su ID.
 */
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {

    /**
     * Busca todos los detalles asociados a una venta específica identificada por su ID.
     *
     * @param ventaId ID de la venta cuyos detalles se desean recuperar.
     * @return Lista de objetos DetalleVenta asociados a la venta especificada.
     */
    List<DetalleVenta> findByVentaId(Integer ventaId);

}