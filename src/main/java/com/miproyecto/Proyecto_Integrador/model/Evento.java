/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miproyecto.Proyecto_Integrador.model;

import jakarta.persistence.*;
import java.time.*;

/**
 * Representa un evento solicitado por un cliente (sin detalles).
 * Compatible con la lógica usada en carrito/pedidos.
 */
@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long idEvento;

    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;
    
    @Column(name = "tipo_evento", length = 100)
    private String tipoevento;
     
     
    @Column(name = "invitados")
    private Integer invitados;

   @Column(name = "detalles", columnDefinition = "text")
   private String detalles;


    @Column(name = "fecha_creacion")
    private Instant fechaCreacion = Instant.now();

    @Column(name = "fecha_evento")
    private LocalDate fechaEvento;

    @Column(name = "hora_evento")
    private LocalTime horaEvento;

    @Column(name = "estado")
    private String estado = "EN_REVISION";

    @Column(name = "distrito", length = 100)
    private String distrito;

    @Column(name = "direccion", columnDefinition = "text")
    private String direccion;

    @Column(name = "referencia", columnDefinition = "text")
    private String referencia;

    @Column(name = "comentarios", columnDefinition = "text")
    private String comentarios;

    // ======== GETTERS Y SETTERS ========

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
      public String getTipoEvento() {
        return tipoevento;
    }

    public void setTipoEvento(String tipoevento) {
        this.tipoevento = tipoevento;
    }
    public Integer getInvitados(){return invitados;}
public void setInvitados(Integer v){this.invitados=v;}
public String getDetalles(){return detalles;}
public void setDetalles(String v){this.detalles=v;}

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public LocalTime getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(LocalTime horaEvento) {
        this.horaEvento = horaEvento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
