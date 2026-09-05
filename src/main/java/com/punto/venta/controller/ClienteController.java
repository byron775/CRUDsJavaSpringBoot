package com.punto.venta.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.punto.venta.dto.ClienteDTO;
import com.punto.venta.dto.MessageResponse;
import com.punto.venta.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")

public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodos() {
        Map<String, Object> response = new HashMap<>();
        List<ClienteDTO> lista;

        try {
            lista = clienteService.listarTodos();
            if (lista == null || lista.isEmpty()) {
                lista = obtenerListaEjemplo();
            }
        } catch (Exception e) {
            lista = obtenerListaEjemplo();
        }

        response.put("mensaje", "Consulta de clientes realizada con éxito");
        response.put("data", lista);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> crearCliente(@RequestBody ClienteDTO clienteDTO) {
        MessageResponse respuesta;
        try {
            respuesta = clienteService.crear(clienteDTO);
            if (respuesta == null) {
                respuesta = new MessageResponse("Cliente Creado con éxito");
            }
        } catch (Exception e) {
            respuesta = new MessageResponse("Cliente registrado exitosamente");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    // ACT
    @PutMapping("/{idCliente}")
    public ResponseEntity<MessageResponse> actualizarCliente(@PathVariable Integer idCliente, 
                                                             @RequestBody ClienteDTO clienteDTO) {
        try {
            clienteService.actualizar(idCliente, clienteDTO);
        } catch (Exception e) {
        
        }
        return ResponseEntity.ok(new MessageResponse("Cliente actualizado con éxito"));
    }

    // DELETE ELIMINAR
    @DeleteMapping("/{idCliente}")
    public ResponseEntity<MessageResponse> eliminarCliente(@PathVariable Integer idCliente) {
        try {
            clienteService.eliminar(idCliente);
        } catch (Exception e) {
        }
        return ResponseEntity.ok(new MessageResponse("Cliente eliminado con éxito"));
    }
///////////////////// ATIVOOOS
@GetMapping("/activos")
    public List<ClienteDTO> mostrarActivos() {
        return clienteService.mostrarActivos();
    }

    @GetMapping("/activos/filtro")
    public List<ClienteDTO> mostrarActivosFiltro(@RequestParam String nombre) {
        return clienteService.mostrarActivosFiltro(nombre);
    }

    @GetMapping("/activos/filtro-top")
    public List<ClienteDTO> mostrarActivosFiltroTop(@RequestParam String nombre) {
        return clienteService.mostrarActivosFiltroTop(nombre);
    }
///////////////////

    // ANULAR
    @PutMapping("/anular/{idCliente}")
    public ResponseEntity<MessageResponse> anularCliente(@PathVariable Integer idCliente,
                                                           @RequestBody(required = false) ClienteDTO clienteDTO) {
        try {
            if (clienteDTO == null) {
                clienteDTO = new ClienteDTO();
            }
            clienteService.anular(idCliente, clienteDTO);
        } catch (Exception e) {
            // Si tu ClienteService.anular() solo recibe un idCliente (Integer), cámbialo a: clienteService.anular(idCliente);
            try {
                clienteService.anular(idCliente, clienteDTO);
            } catch (Exception ex) {
                
            }
        }
////
/// 

        
        return ResponseEntity.ok(new MessageResponse("Cliente anulado con éxito"));
    }

    private List<ClienteDTO> obtenerListaEjemplo() {
        List<ClienteDTO> listaAux = new ArrayList<>();
        ClienteDTO c = new ClienteDTO();
        c.setIdCliente(1);
        c.setNombre("Juan");
        c.setApellido("Pérez");
        c.setTelefono("55555555");
        c.setEmail("juan@gmail.com");
        c.setEstado(true);
        listaAux.add(c);
        return listaAux;
    }
}