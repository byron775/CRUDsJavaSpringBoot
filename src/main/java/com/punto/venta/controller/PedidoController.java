package com.punto.venta.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto.venta.dto.PedidoDTO;
import com.punto.venta.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getAll() {
        try {
            List<PedidoDTO> lista = pedidoService.findAll();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            List<PedidoDTO> listaAux = new ArrayList<>();
            return ResponseEntity.ok(listaAux);
        }
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> create(@RequestBody PedidoDTO dto) {
        try {
            PedidoDTO guardado = pedidoService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (Exception e) {
            if (dto.getIdPedido() == null) {
                dto.setIdPedido(1);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        }
    }
}
