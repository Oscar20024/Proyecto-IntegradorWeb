
package com.miproyecto.Proyecto_Integrador.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Instant;

// src/main/java/com/miproyecto/Proyecto_Integrador/model/Pedido.java
@Entity
@Table(name = "pedidos")
public class Pedido {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_pedido")
  private Long idPedido;

  @Column(name = "id_cliente", nullable = false)
  private Long idCliente;

  @Column(name = "fecha_pedido")
  private Instant fechaPedido = Instant.now();

  @Column(name = "fecha_entrega")  private LocalDate fechaEntrega;
  @Column(name = "hora_entrega")   private LocalTime horaEntrega;
  @Column(name = "estado")         private String estado;
  @Column(name = "total", precision = 10, scale = 2)
  private BigDecimal total;

  // 👇 NUEVO
  @Column(name = "direccion", columnDefinition = "text")
  private String direccion;

  @Column(name = "distrito", length = 100)
  private String distrito;

  @Column(name = "referencia", columnDefinition = "text")
  private String referencia;

  @Column(name="comentarios", columnDefinition="text")
private String comentarios;
  
  // getters/setters…
  public Long getIdPedido() { return idPedido; }

  public Long getIdCliente() { return idCliente; }
  public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

  public Instant getFechaPedido() { return fechaPedido; }
  public void setFechaPedido(Instant v) { this.fechaPedido = v; }

  public LocalDate getFechaEntrega() { return fechaEntrega; }
  public void setFechaEntrega(LocalDate v) { this.fechaEntrega = v; }

  public LocalTime getHoraEntrega() { return horaEntrega; }
  public void setHoraEntrega(LocalTime v) { this.horaEntrega = v; }

  public String getEstado() { return estado; }
  public void setEstado(String v) { this.estado = v; }

  public BigDecimal getTotal() { return total; }
  public void setTotal(BigDecimal v) { this.total = v; }

  // 👇 NUEVOS getters/setters
  public String getDireccion() { return direccion; }
  public void setDireccion(String direccion) { this.direccion = direccion; }

  public String getDistrito() { return distrito; }
  public void setDistrito(String distrito) { this.distrito = distrito; }

  public String getReferencia() { return referencia; }
  public void setReferencia(String referencia) { this.referencia = referencia; }
  
  public String getComentarios(){ return comentarios; }
public void setComentarios(String c){ this.comentarios = c; }
}
