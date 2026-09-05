package com.punto.venta.repository;

import com.punto.venta.entity.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    // AAAAAAAAAAAAAAAAAAAAAmostrarActivos
    List<Pedido> findByEstadoTrueOrderByIdPedidoDesc();

    // AAAAAAAAAAAAAAAAAAAAAA mostrarActivosFiltro (Filtra por ID de Cliente mediante la relación idCliente.idCliente)
    List<Pedido> findByEstadoTrueAndIdCliente_IdCliente(Integer idCliente);

    // AAAAAAAAAAAAAAAAAAAAAAAAAAA mostrarActivosFiltroTop
    List<Pedido> findTop3ByEstadoTrueAndIdCliente_IdClienteOrderByIdPedidoDesc(Integer idCliente);
}