package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.punto.venta.dto.ProductoDTO;
import com.punto.venta.entity.Producto;
import com.punto.venta.repository.CategoriaRepository;
import com.punto.venta.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductoDTO guardarProducto(ProductoDTO dto) {
        Producto producto = convertToEntity(dto);
        Producto guardado = productoRepository.save(producto);
        return convertToDTO(guardado);
    }

    private ProductoDTO convertToDTO(Producto c) {
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(c.getIdProducto());
        dto.setNombre(c.getNombre());
        dto.setDescripcion(c.getDescripcion());
        dto.setPrecio(c.getPrecio());
        dto.setStock(c.getStock());
        
        // Uso de getIdCategoria() en lugar de getCategoria()
        if (c.getIdCategoria() != null) {
            dto.setIdCategoria(c.getIdCategoria().getIdCategoria());
        }
        return dto;
    }

    private Producto convertToEntity(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setIdProducto(dto.getIdProducto());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        
        // Uso de setIdCategoria(...) con expresión lambda limpia
        if (dto.getIdCategoria() != null) {
            categoriaRepository.findById(dto.getIdCategoria())
                    .ifPresent(cat -> producto.setIdCategoria(cat));
        }
        return producto;
    }
}