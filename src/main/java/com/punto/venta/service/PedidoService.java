package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.punto.venta.dto.PedidoDTO;
import com.punto.venta.entity.Cliente;
import com.punto.venta.entity.Pedido;
import com.punto.venta.repository.ClienteRepository;
import com.punto.venta.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<PedidoDTO> findAll() {
        return pedidoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO save(PedidoDTO dto) {
        Pedido pedido = convertToEntity(dto);
        Pedido guardado = pedidoRepository.save(pedido);
        return convertToDTO(guardado);
    }

    private PedidoDTO convertToDTO(Pedido p) {
        PedidoDTO dto = new PedidoDTO();
        dto.setIdPedido(p.getIdPedido());
        // Uso de getFechaPedido() en lugar de getFecha()
        dto.setFecha(p.getFechaPedido());
        dto.setTotal(p.getTotal());
        if (p.getIdCliente() != null) {
            dto.setIdCliente(p.getIdCliente().getIdCliente());
        }
        return dto;
    }

    private Pedido convertToEntity(PedidoDTO dto) {
        Pedido p = new Pedido();
        p.setIdPedido(dto.getIdPedido());
        // Uso de setFechaPedido() en lugar de setFecha()
        p.setFechaPedido(dto.getFecha());
        p.setTotal(dto.getTotal());
        if (dto.getIdCliente() != null) {
            Cliente cliente = clienteRepository.findById(dto.getIdCliente()).orElse(null);
            p.setIdCliente(cliente);
        }
        return p;
    }
}
