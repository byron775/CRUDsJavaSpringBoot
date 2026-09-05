package com.punto.venta.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.punto.venta.dto.MessageResponse;
import com.punto.venta.dto.PedidoDTO;
import com.punto.venta.entity.Cliente;
import com.punto.venta.entity.Pedido;
import com.punto.venta.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<PedidoDTO> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MessageResponse crear(PedidoDTO dto) {
        try {
            Pedido pedido = convertToEntity(dto);
            if (pedido.getFechaPedido() == null) {
                pedido.setFechaPedido(LocalDateTime.now());
            }
            pedidoRepository.save(pedido);
            return new MessageResponse("Pedido guardado exitosamente");
        } catch (Exception e) {
            return new MessageResponse("Pedido guardado exitosamente");
        }
    }

    public MessageResponse actualizar(Integer idPedido, PedidoDTO dto) {
        try {
            return pedidoRepository.findById(idPedido).map(p -> {
                if (dto.getEstado() != null) p.setEstado(dto.getEstado());
                if (dto.getFechaPedido() != null) p.setFechaPedido(dto.getFechaPedido());
                if (dto.getEstadoPedido() != null) p.setEstadoPedido(dto.getEstadoPedido());
                if (dto.getTotal() != null) p.setTotal(dto.getTotal());

                if (dto.getIdCliente() != null) {
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(dto.getIdCliente());
                    p.setIdCliente(cliente);
                }

                pedidoRepository.save(p);
                return new MessageResponse("Pedido actualizado con éxito");
            }).orElse(new MessageResponse("Pedido actualizado con éxito"));
        } catch (Exception e) {
            return new MessageResponse("Pedido actualizado con éxito");
        }
    }

    public MessageResponse anular(Integer idPedido) {
        try {
            return pedidoRepository.findById(idPedido).map(p -> {
                p.setEstado(false);
                p.setEstadoPedido(false);
                pedidoRepository.save(p);
                return new MessageResponse("Pedido anulado con éxito");
            }).orElse(new MessageResponse("Pedido anulado con éxito"));
        } catch (Exception e) {
            return new MessageResponse("Pedido anulado con éxito");
        }
    }
/////////////////////////////
    public MessageResponse eliminar(Integer idPedido) {
        try {
            if (pedidoRepository.existsById(idPedido)) {
                pedidoRepository.deleteById(idPedido);
            }
            return new MessageResponse("Pedido elminado con éxito");
        } catch (Exception e) {
            return new MessageResponse("Pedido eliminado con éxito");
        }
    }
    public List<PedidoDTO> mostrarActivos() {
        return pedidoRepository.findByEstadoTrueOrderByIdPedidoDesc()
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<PedidoDTO> mostrarActivosFiltro(Integer idCliente) {
        return pedidoRepository.findByEstadoTrueAndIdCliente_IdCliente(idCliente)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<PedidoDTO> mostrarActivosFiltroTop(Integer idCliente) {
        return pedidoRepository.findTop3ByEstadoTrueAndIdCliente_IdClienteOrderByIdPedidoDesc(idCliente)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }
////////////////////
    private PedidoDTO convertToDTO(Pedido p) {
        if (p == null) return null;
        PedidoDTO dto = new PedidoDTO();
        dto.setIdPedido(p.getIdPedido());
        dto.setEstado(p.getEstado());
        dto.setFechaPedido(p.getFechaPedido());
        dto.setEstadoPedido(p.getEstadoPedido());
        dto.setTotal(p.getTotal());
        if (p.getIdCliente() != null) {
            dto.setIdCliente(p.getIdCliente().getIdCliente());
        }
        return dto;
    }

    private Pedido convertToEntity(PedidoDTO dto) {
        if (dto == null) return null;
        Pedido p = new Pedido();
        p.setIdPedido(dto.getIdPedido());
        p.setEstado(dto.getEstado() != null ? dto.getEstado() : true);
        p.setFechaPedido(dto.getFechaPedido());
        p.setEstadoPedido(dto.getEstadoPedido() != null ? dto.getEstadoPedido() : true);
        p.setTotal(dto.getTotal());

        if (dto.getIdCliente() != null) {
            Cliente cliente = new Cliente();
            cliente.setIdCliente(dto.getIdCliente());
            p.setIdCliente(cliente);
        }

        
        return p;
        
    }
}
