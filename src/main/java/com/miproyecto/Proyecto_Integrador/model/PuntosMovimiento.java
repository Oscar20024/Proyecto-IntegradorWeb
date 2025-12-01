package com.miproyecto.Proyecto_Integrador.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "puntos_movimientos")
public class PuntosMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimiento;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_pedido_detalle")
    private PedidoDetalle pedidoDetalle;

    @Column(name = "producto_id")
    private Long productoId;   // 👈 OJO: solo guardamos el id del producto

    private String tipo;       // "GANADO" o "CANJEADO"
    private Integer puntos;
    private String descripcion;

    private LocalDateTime fecha = LocalDateTime.now();

    // ==== GETTERS & SETTERS ====
    public Long getIdMovimiento() { return idMovimiento; }
    public void setIdMovimiento(Long idMovimiento) { this.idMovimiento = idMovimiento; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public PedidoDetalle getPedidoDetalle() { return pedidoDetalle; }
    public void setPedidoDetalle(PedidoDetalle pedidoDetalle) { this.pedidoDetalle = pedidoDetalle; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getPuntos() { return puntos; }
    public void setPuntos(Integer puntos) { this.puntos = puntos; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
