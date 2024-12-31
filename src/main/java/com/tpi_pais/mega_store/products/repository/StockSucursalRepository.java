package com.tpi_pais.mega_store.products.repository;

import com.tpi_pais.mega_store.products.model.MovimientoStock;
import com.tpi_pais.mega_store.products.model.StockSucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Interface for CRUD operations on a repository of {@link StockSucursal}.
 * Provides methods for retrieving stock movement records, particularly by product.
 */
@Repository
public interface StockSucursalRepository extends JpaRepository<StockSucursal, Integer> {

    public ArrayList<StockSucursal> findByProductoIdOrderByStockDesc (Integer idProducto);

    public Optional<StockSucursal> findByProductoIdAndSucursalId (Integer idProducto, Integer idSucursal);

}
