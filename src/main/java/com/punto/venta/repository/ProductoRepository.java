package com.punto.venta.repository;

import com.punto.venta.entity.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    /////////// mostrarActivos
    List<Producto> findByEstadoTrueOrderByIdProductoDesc();

    /////////////////// mostrarActivosFiltro
    List<Producto> findByEstadoTrueAndNombreContainingIgnoreCase(String nombre);

    //////////////// mostraractivosFiltroTop
    List<Producto> findTop3ByEstadoTrueAndNombreContainingIgnoreCaseOrderByIdProductoDesc(String nombre);
}