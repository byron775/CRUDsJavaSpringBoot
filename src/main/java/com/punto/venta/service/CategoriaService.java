package com.punto.venta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.punto.venta.dto.CategoriaDTO;
import com.punto.venta.entity.Categoria;
import com.punto.venta.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDTO> findAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CategoriaDTO save(CategoriaDTO dto) {
        Categoria categoria = convertToEntity(dto);
        Categoria guardada = categoriaRepository.save(categoria);
        return convertToDTO(guardada);
    }

    public void eliminarCantegoria(Integer idCategoria) {
        if (!categoriaRepository.existsById(idCategoria)) {
            throw new RuntimeException("La categoria no existe con id " + idCategoria);
        }
        categoriaRepository.deleteById(idCategoria);
    }

    public CategoriaDTO anularCategoria(Integer idCategoria) {
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("La categoria no existe con id " + idCategoria));

        categoria.setEstado(false);
        Categoria savedCategoria = categoriaRepository.save(categoria);
        return convertToDTO(savedCategoria);
    }

    public CategoriaDTO modificarCategoria(Integer idCategoria, CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("La categoria no existe con id " + idCategoria));

        if (categoriaDTO.getNombre() != null) {
            categoria.setNombre(categoriaDTO.getNombre());
        }
        if (categoriaDTO.getDescripcion() != null) {
            categoria.setDescripcion(categoriaDTO.getDescripcion());
        }
        if (categoriaDTO.getEstado() != null) {
            categoria.setEstado(categoriaDTO.getEstado());
        }

        Categoria savedCategoria = categoriaRepository.save(categoria);
        return convertToDTO(savedCategoria);
    }

    private CategoriaDTO convertToDTO(Categoria c) {
        if (c == null) return null;
        CategoriaDTO dto = new CategoriaDTO();
        dto.setIdCategoria(c.getIdCategoria());
        dto.setNombre(c.getNombre());
        dto.setDescripcion(c.getDescripcion());
        dto.setEstado(c.getEstado());
        return dto;
    }

    private Categoria convertToEntity(CategoriaDTO dto) {
        if (dto == null) return null;
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        categoria.setEstado(dto.getEstado() != null ? dto.getEstado() : true);
        return categoria;
    }


    public List<CategoriaDTO> mostrarActivos() {
        return categoriaRepository.findByEstadoTrueOrderByIdCategoriaDesc()
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<CategoriaDTO> mostrarActivosFiltro(String nombre) {
        return categoriaRepository.findByEstadoTrueAndNombreContainingIgnoreCase(nombre)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<CategoriaDTO> mostrarActivosFiltroTop(String nombre) {
        return categoriaRepository.findTop3ByEstadoTrueAndNombreContainingIgnoreCaseOrderByIdCategoriaDesc(nombre)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    
}
