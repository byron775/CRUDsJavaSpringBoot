package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.punto.venta.dto.PedidoDetalleDTO;
import com.punto.venta.entity.PedidoDetalle;
import com.punto.venta.repository.PedidoDetalleRepository;
import com.punto.venta.repository.PedidoRepository;
import com.punto.venta.repository.ProductoRepository;

@Service
public class PedidoDetalleService {

    private final PedidoDetalleRepository pedidoDetalleRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public PedidoDetalleService(PedidoDetalleRepository pedidoDetalleRepository,
                    PedidoRepository pedidoRepository,
                ProductoRepository productoRepository) {
        this.pedidoDetalleRepository = pedidoDetalleRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    public List<PedidoDetalleDTO> findAll() {
        return pedidoDetalleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PedidoDetalleDTO save(PedidoDetalleDTO dto) {
        PedidoDetalle detalle = convertToEntity(dto);
        PedidoDetalle guardado = pedidoDetalleRepository.save(detalle);
        return convertToDTO(guardado);
    }

    private PedidoDetalleDTO convertToDTO(PedidoDetalle d) {
        PedidoDetalleDTO dto = new PedidoDetalleDTO();
        dto.setIdPedidoDetalle(d.getIdPedidoDetalle());
        dto.setCantidad(d.getCantidad());
        dto.setPrecioUnitario(d.getPrecioUnitario());
        if (d.getIdPedido() != null) {
            dto.setIdPedido(d.getIdPedido().getIdPedido());
        }
        if (d.getIdProducto() != null) {
            dto.setIdProducto(d.getIdProducto().getIdProducto());
        }
        return dto;
    }

    private PedidoDetalle convertToEntity(PedidoDetalleDTO dto) {
        PedidoDetalle d = new PedidoDetalle();
        d.setIdPedidoDetalle(dto.getIdPedidoDetalle());
        d.setCantidad(dto.getCantidad());
        d.setPrecioUnitario(dto.getPrecioUnitario());

        if (dto.getIdPedido() != null) {
            pedidoRepository.findById(dto.getIdPedido())
                    .ifPresent(ped -> d.setIdPedido(ped));
        }

        if (dto.getIdProducto() != null) {
            productoRepository.findById(dto.getIdProducto())
                    .ifPresent(prod -> d.setIdProducto(prod));
        }

        return d;
    }
}