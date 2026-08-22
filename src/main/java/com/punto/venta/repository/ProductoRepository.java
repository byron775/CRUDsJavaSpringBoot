package com.punto.venta.repository;

import org.springframework.data.jpa.repository.JpaRepository;



import com.punto.venta.entity.Producto;


public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}