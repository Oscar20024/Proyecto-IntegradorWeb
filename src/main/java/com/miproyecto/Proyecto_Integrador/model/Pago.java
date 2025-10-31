package com.miproyecto.Proyecto_Integrador.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @Column(precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(length = 50)
    private String metodo;

    @Column(name = "estado_pago", length = 20)
    private String estadoPago;

    @Column(name = "fecha_registro")
    private Instant fechaRegistro = Instant.now();

    // 👇👇  Mapea a las columnas que tienes en la BD
    @Column(name = "comprobante_url")          // <--- antes era ruta_comprobante
    private String comprobanteUrl;

    @Column(name = "comprobante_storage_path")
    private String comprobanteStoragePath;

    @Column(name = "comprobante_mime")
    private String comprobanteMime;

    @Column(name = "comprobante_size")
    private Integer comprobanteSize;

    // ===== getters / setters =====
    public Long getIdPago() { return idPago; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }

    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }

    public Instant getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Instant fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getComprobanteUrl() { return comprobanteUrl; }
    public void setComprobanteUrl(String comprobanteUrl) { this.comprobanteUrl = comprobanteUrl; }

    public String getComprobanteStoragePath() { return comprobanteStoragePath; }
    public void setComprobanteStoragePath(String comprobanteStoragePath) { this.comprobanteStoragePath = comprobanteStoragePath; }

    public String getComprobanteMime() { return comprobanteMime; }
    public void setComprobanteMime(String comprobanteMime) { this.comprobanteMime = comprobanteMime; }

    public Integer getComprobanteSize() { return comprobanteSize; }
    public void setComprobanteSize(Integer comprobanteSize) { this.comprobanteSize = comprobanteSize; }
}
