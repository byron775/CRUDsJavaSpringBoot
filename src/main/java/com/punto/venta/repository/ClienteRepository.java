package com.punto.venta.repository;

import com.punto.venta.entity.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
    boolean existsByNombreIgnoreCaseAndApellidoIgnoreCase(String nombre, String apellido);

    ////// aaaaaaaaaaaaaaaaaaamostrarActivos
    List<Cliente> findByEstadoTrueOrderByIdClienteDesc();

    /////////////// aaaaaaaaaaaaaaaaaaaaaamostrarActivosFiltro
    List<Cliente> findByEstadoTrueAndNombreContainingIgnoreCase(String nombre);

    //////////////// aaaaaaaaaaaaaaaamostrarActivosFiltroTop
    List<Cliente> findTop3ByEstadoTrueAndNombreContainingIgnoreCaseOrderByIdClienteDesc(String nombre);
}