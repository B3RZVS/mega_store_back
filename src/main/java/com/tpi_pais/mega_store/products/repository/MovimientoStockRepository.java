package com.tpi_pais.mega_store.products.repository;

import com.tpi_pais.mega_store.products.model.MovimientoStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MovimientoStockRepository extends JpaRepository<MovimientoStock,Integer> {

    //Movimientos de stock por producto
    public List<MovimientoStock> findByProductoIdOrderByFechaCreacionDesc(Integer idProducto);

}
