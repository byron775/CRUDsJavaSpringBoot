package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.punto.venta.dto.ClienteDTO;
import com.punto.venta.entity.Cliente;
import com.punto.venta.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO crear(ClienteDTO dto) {
        try {
            if (dto.getNombre() != null && dto.getApellido() != null &&
                clienteRepository.existsByNombreIgnoreCaseAndApellidoIgnoreCase(dto.getNombre(), dto.getApellido())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El cliente ya existe");
            }
            Cliente cliente = convertToEntity(dto);
            return convertToDTO(clienteRepository.save(cliente));
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al guardar el cliente: " + e.getMessage());
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
}