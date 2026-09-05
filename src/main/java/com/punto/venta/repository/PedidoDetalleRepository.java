package com.punto.venta.repository;

import com.punto.venta.entity.PedidoDetalle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Integer> {

    /////////////////////  mostrarActivos
    List<PedidoDetalle> findByIdPedido_EstadoTrueOrderByIdPedidoDetalleDesc();

    // aaaaaaaaaaaaaaaaaaaaa mostrarActivos Filtro
    List<PedidoDetalle> findByIdPedido_EstadoTrueAndIdPedido_IdPedido(Integer idPedido);

    // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA mostrarActivos filtro Top
    List<PedidoDetalle> findTop3ByIdPedido_EstadoTrueAndIdPedido_IdPedidoOrderByIdPedidoDetalleDesc(Integer idPedido);
}