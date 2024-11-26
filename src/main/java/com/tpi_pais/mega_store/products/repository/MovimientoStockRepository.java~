package com.tpi_pais.mega_store.products.repository;

import com.tpi_pais.mega_store.products.model.MovimientoStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoStockRepository extends JpaRepository<MovimientoStock,Integer> {

    //Movimientos de stock por producto
    public List<MovimientoStock> findByProductoIdInOrderByFechaCreacionDesc(Integer idProducto);

}
