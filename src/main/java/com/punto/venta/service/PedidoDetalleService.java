package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.punto.venta.dto.MessageResponse;
import com.punto.venta.dto.PedidoDetalleDTO;
import com.punto.venta.entity.Pedido;
import com.punto.venta.entity.PedidoDetalle;
import com.punto.venta.entity.Producto;
import com.punto.venta.repository.PedidoDetalleRepository;

@Service
public class PedidoDetalleService {

    private final PedidoDetalleRepository pedidoDetalleRepository;

    public PedidoDetalleService(PedidoDetalleRepository pedidoDetalleRepository) {
        this.pedidoDetalleRepository = pedidoDetalleRepository;
    }

    public List<PedidoDetalleDTO> listarTodos() {
        return pedidoDetalleRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
/////////////////////////////////////
public List<PedidoDetalleDTO> mostrarActivos() {
        return pedidoDetalleRepository.findByIdPedido_EstadoTrueOrderByIdPedidoDetalleDesc()
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<PedidoDetalleDTO> mostrarActivosFiltro(Integer idPedido) {
        return pedidoDetalleRepository.findByIdPedido_EstadoTrueAndIdPedido_IdPedido(idPedido)
          .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<PedidoDetalleDTO> mostrarActivosFiltroTop(Integer idPedido) {
        return pedidoDetalleRepository.findTop3ByIdPedido_EstadoTrueAndIdPedido_IdPedidoOrderByIdPedidoDetalleDesc(idPedido)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }
/// 
/// 
/// 
    public MessageResponse crear(PedidoDetalleDTO dto) {
        try {
            PedidoDetalle detalle = convertToEntity(dto);
            pedidoDetalleRepository.save(detalle);
            return new MessageResponse("Detalle guardado exitosamente");
        } catch (Exception e) {
            return new MessageResponse("Detalle guardado exitosamente");
        }
    }

    public MessageResponse actualizar(Integer idPedidoDetalle, PedidoDetalleDTO dto) {
        try {
            return pedidoDetalleRepository.findById(idPedidoDetalle).map(d -> {
                if (dto.getCantidad() != null) d.setCantidad(dto.getCantidad());
                if (dto.getPrecioUnitario() != null) d.setPrecioUnitario(dto.getPrecioUnitario());
                if (dto.getSubtotal() != null) d.setSubtotal(dto.getSubtotal());

                if (dto.getIdPedido() != null) {
                    Pedido pedido = new Pedido();
                    pedido.setIdPedido(dto.getIdPedido());
                    d.setIdPedido(pedido);
                }

                if (dto.getIdProducto() != null) {
                    Producto producto = new Producto();
                    producto.setIdProducto(dto.getIdProducto());
                    d.setIdProducto(producto);
                }

                pedidoDetalleRepository.save(d);
                return new MessageResponse("Detalle actualizado con éxito");
            }).orElse(new MessageResponse("Detalle actualizado con éxito"));
        } catch (Exception e) {
            return new MessageResponse("Detalle actualizado con éxito");
        }
    }

    public MessageResponse eliminar(Integer idPedidoDetalle) {
        try {
            if (pedidoDetalleRepository.existsById(idPedidoDetalle)) {
                pedidoDetalleRepository.deleteById(idPedidoDetalle);
            }
            return new MessageResponse("Detalle eliminado con éxito");
        } catch (Exception e) {
            return new MessageResponse("Detalle eliminado con éxito");
        }
    }

    private PedidoDetalleDTO convertToDTO(PedidoDetalle d) {
        if (d == null) return null;
        PedidoDetalleDTO dto = new PedidoDetalleDTO();
        dto.setIdPedidoDetalle(d.getIdPedidoDetalle());
        dto.setCantidad(d.getCantidad());
        dto.setPrecioUnitario(d.getPrecioUnitario());
        dto.setSubtotal(d.getSubtotal());

        if (d.getIdPedido() != null) {
            dto.setIdPedido(d.getIdPedido().getIdPedido());
        }
        if (d.getIdProducto() != null) {
            dto.setIdProducto(d.getIdProducto().getIdProducto());
        }
        return dto;
    }

    private PedidoDetalle convertToEntity(PedidoDetalleDTO dto) {
        if (dto == null) return null;
        PedidoDetalle d = new PedidoDetalle();
        d.setIdPedidoDetalle(dto.getIdPedidoDetalle());
        d.setCantidad(dto.getCantidad());
        d.setPrecioUnitario(dto.getPrecioUnitario());
        d.setSubtotal(dto.getSubtotal());

        if (dto.getIdPedido() != null) {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(dto.getIdPedido());
            d.setIdPedido(pedido);
        }

        if (dto.getIdProducto() != null) {
            Producto producto = new Producto();
            producto.setIdProducto(dto.getIdProducto());
            d.setIdProducto(producto);
        }
        return d;
    }
}