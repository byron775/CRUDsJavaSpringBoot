package com.punto.venta.repository;

import com.punto.venta.entity.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    
    // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA mostrr LOS Activos
    List<Categoria> findByEstadoTrueOrderByIdCategoriaDesc();

    // AAAAAAAAAAAAAAAAAAAAAAAAAAA mostrarActivos Filtro
    List<Categoria> findByEstadoTrueAndNombreContainingIgnoreCase(String nombre);

    // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA mostrarActivosFiltroTop
    List<Categoria> findTop3ByEstadoTrueAndNombreContainingIgnoreCaseOrderByIdCategoriaDesc(String nombre);
}