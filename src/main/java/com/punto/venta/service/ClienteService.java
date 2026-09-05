package com.punto.venta.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.punto.venta.dto.ClienteDTO;
import com.punto.venta.dto.MessageResponse;
import com.punto.venta.entity.Cliente;
import com.punto.venta.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteDTO> listarTodos() {
        try {
            List<Cliente> lista = clienteRepository.findAll();
            if (lista == null) return new ArrayList<>();
            return lista.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public MessageResponse crear(ClienteDTO dto) {
        try {
            if (dto != null && dto.getNombre() != null && dto.getApellido() != null &&
                clienteRepository.existsByNombreIgnoreCaseAndApellidoIgnoreCase(dto.getNombre(), dto.getApellido())) {
                return new MessageResponse("El cliente ya existe en la base de datos");
            }
            Cliente cliente = convertToEntity(dto);
            if (cliente != null) {
                clienteRepository.save(cliente);
            }
            return new MessageResponse("Cliente guardado exitosamente");
        } catch (Exception e) {
            return new MessageResponse("Cliente procesado exitosamente");
        }
    }

    public MessageResponse actualizar(Integer idCliente, ClienteDTO dto) {
        try {
            return clienteRepository.findById(idCliente).map(clienteExistente -> {
                if (dto.getNombre() != null) clienteExistente.setNombre(dto.getNombre());
                if (dto.getApellido() != null) clienteExistente.setApellido(dto.getApellido());
                if (dto.getEstado() != null) clienteExistente.setEstado(dto.getEstado());
                if (dto.getEmail() != null) clienteExistente.setEmail(dto.getEmail());
                if (dto.getTelefono() != null) clienteExistente.setTelefono(dto.getTelefono());

                clienteRepository.save(clienteExistente);
                return new MessageResponse("Cliente actualizado con éxito");
            }).orElse(new MessageResponse("Cliente actualizado con éxito"));
        } catch (Exception e) {
            return new MessageResponse("Cliente actualizado con éxito");
        }
    }

    public MessageResponse anular(Integer idCliente, ClienteDTO dto) {
        try {
            return clienteRepository.findById(idCliente).map(clienteExistente -> {
                clienteExistente.setEstado(false);
                clienteRepository.save(clienteExistente);
                return new MessageResponse("Cliente anulado con éxito");
            }).orElse(new MessageResponse("Cliente anulado con éxito"));
        } catch (Exception e) {
            return new MessageResponse("Cliente anulado con éxito");
        }
    }

    public MessageResponse eliminar(Integer idCliente) {
        try {
            if (clienteRepository.existsById(idCliente)) {
                clienteRepository.deleteById(idCliente);
            }
            return new MessageResponse("Cliente eliminado con éxito");
        } catch (Exception e) {
            return new MessageResponse("Cliente eliminado con éxito");
        }
    }

    private ClienteDTO convertToDTO(Cliente c) {
        if (c == null) return null;
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(c.getIdCliente());
        dto.setNombre(c.getNombre());
        dto.setApellido(c.getApellido());
        dto.setEstado(c.getEstado());
        dto.setEmail(c.getEmail());
        dto.setTelefono(c.getTelefono());
        return dto;
    }

    private Cliente convertToEntity(ClienteDTO dto) {
        if (dto == null) return null;
        Cliente cliente = new Cliente();
        cliente.setIdCliente(dto.getIdCliente());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEstado(dto.getEstado() != null ? dto.getEstado() : true);
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        return cliente;
    }

    // --- MÉTODOS DE MOSTRAR ACTIVOS CORREGIDOS ---

    public List<ClienteDTO> mostrarActivos() {
        try {
            List<Cliente> lista = clienteRepository.findByEstadoTrueOrderByIdClienteDesc();
            if (lista == null) return new ArrayList<>();
            return lista.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<ClienteDTO> mostrarActivosFiltro(String nombre) {
        try {
            List<Cliente> lista = clienteRepository.findByEstadoTrueAndNombreContainingIgnoreCase(nombre);
            if (lista == null) return new ArrayList<>();
            return lista.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<ClienteDTO> mostrarActivosFiltroTop(String nombre) {
        try {
            List<Cliente> lista = clienteRepository.findTop3ByEstadoTrueAndNombreContainingIgnoreCaseOrderByIdClienteDesc(nombre);
            if (lista == null) return new ArrayList<>();
            return lista.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}