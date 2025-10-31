/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miproyecto.Proyecto_Integrador.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;



import jakarta.persistence.*;
import java.math.BigDecimal;


import jakarta.persistence.*;
import java.math.BigDecimal;

import jakarta.persistence.*;
import java.math.BigDecimal;


import jakarta.persistence.*;
import java.math.BigDecimal;


import jakarta.persistence.*;
import java.math.BigDecimal;


import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedido") // <--- coincide con tu SQL
public class PedidoDetalle {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_detalle")
  private Long idDetalle;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_pedido", nullable = false)
  private Pedido pedido;

  @Column(name = "id_producto", nullable = false)
  private Long idProducto; // requerido por tu SQL

  @Column(name = "cantidad", nullable = false)
  private Integer cantidad;

  @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
  private BigDecimal precioUnitario;

  @Column(name = "nombre_producto")  // opcional: si quieres guardar el nombre también
  private String nombreProducto;

  // getters/setters
  public Long getIdDetalle() { return idDetalle; }
  public Pedido getPedido() { return pedido; }
  public void setPedido(Pedido p) { this.pedido = p; }
  public Long getIdProducto() { return idProducto; }
  public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }
  public Integer getCantidad() { return cantidad; }
  public void setCantidad(Integer c) { this.cantidad = c; }
  public BigDecimal getPrecioUnitario() { return precioUnitario; }
  public void setPrecioUnitario(BigDecimal p) { this.precioUnitario = p; }
  public String getNombreProducto() { return nombreProducto; }
  public void setNombreProducto(String s) { this.nombreProducto = s; }
}
