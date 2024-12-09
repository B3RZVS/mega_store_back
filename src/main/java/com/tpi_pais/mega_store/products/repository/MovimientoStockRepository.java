package com.tpi_pais.mega_store.products.repository;

import com.tpi_pais.mega_store.products.model.MovimientoStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface for CRUD operations on a repository of {@link MovimientoStock}.
 * Provides methods for retrieving stock movement records, particularly by product.
 */
@Repository
public interface MovimientoStockRepository extends JpaRepository<MovimientoStock, Integer> {

    /**
     * Retrieves the list of stock movements for a specific product, ordered by the creation date in descending order.
     *
     * @param idProducto ID of the product whose stock movements are to be retrieved.
     * @return List of stock movements for the specified product, ordered by the most recent.
     */
    public List<MovimientoStock> findByProductoIdOrderByFechaCreacionDesc(Integer idProducto);

}
