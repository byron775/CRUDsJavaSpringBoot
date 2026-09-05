package com.punto.venta.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.punto.venta.dto.ProductoDTO;
import com.punto.venta.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<ProductoDTO> listarTodos() {
        return productoService.listarProductos();
    }

    @PostMapping
    public ProductoDTO guardar(@RequestBody ProductoDTO dto) {
        return productoService.guardarProducto(dto);
    }

    @GetMapping("/activos")
    public List<ProductoDTO> mostrarActivos() {
        return productoService.mostrarActivos();
    }

    @GetMapping("/activos/filtro")
    public List<ProductoDTO> mostrarActivosFiltro(@RequestParam String nombre) {
        return productoService.mostrarActivosFiltro(nombre);
    }

    @GetMapping("/activos/filtro-top")
    public List<ProductoDTO> mostrarActivosFiltroTop(@RequestParam String nombre) {
        return productoService.mostrarActivosFiltroTop(nombre);
    }

}