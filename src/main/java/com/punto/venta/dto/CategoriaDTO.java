package com.punto.venta.dto;

import lombok.Data;

@Data
public class CategoriaDTO {
    private Integer idCategoria;
    private boolean estado;
    private String nombre;
    private String descripcion;

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}