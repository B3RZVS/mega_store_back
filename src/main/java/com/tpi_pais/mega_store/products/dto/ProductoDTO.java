package com.tpi_pais.mega_store.products.dto;
import com.tpi_pais.mega_store.utils.StringUtils;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class ProductoDTO {
    private Integer id;
    private String nombre;
    @Getter
    private LocalDateTime fechaEliminacion;
    private BigDecimal precio;
    private BigDecimal peso;
    private Integer stockMedio;
    private Integer stockMinimo;
    private String foto;

    public boolean noTieneNombre (){
        return this.getNombre() == null || this.getNombre() == "";
    }

    public void capitalizarNombre (){
        StringUtils stringUtils = new StringUtils();
        this.setNombre(stringUtils.capitalizeWords(this.getNombre()));
    }

}
