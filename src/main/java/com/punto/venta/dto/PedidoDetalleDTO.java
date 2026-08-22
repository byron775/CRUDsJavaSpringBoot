package com.punto.venta.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDetalleDTO {

    private Integer idPedidoDetalle;
    private Integer idPedido;
    private Integer idProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}