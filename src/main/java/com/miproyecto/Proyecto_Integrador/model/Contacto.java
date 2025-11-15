
package com.miproyecto.Proyecto_Integrador.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "contactos")
public class Contacto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_contacto")
  private Long idContacto;

  @Column(name = "id_cliente")
  private Long idCliente; // puede ser null (anónimo)

  @Column(name = "nombre", length = 120, nullable = false)
  private String nombre;

  @Column(name = "telefono", length = 30)
  private String telefono;

  @Column(name = "email", length = 120)
  private String email;

  @Column(name = "mensaje", columnDefinition = "text")
  private String mensaje;

  @Column(name = "estado", length = 30, nullable = false)
  private String estado = "EN_REVISION";

  @Column(name = "fecha_creacion", updatable = false)
  private Instant fechaCreacion = Instant.now();

  // getters/setters
  public Long getIdContacto() { return idContacto; }
  public void setIdContacto(Long idContacto) { this.idContacto = idContacto; }
  public Long getIdCliente() { return idCliente; }
  public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }
  public String getTelefono() { return telefono; }
  public void setTelefono(String telefono) { this.telefono = telefono; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getMensaje() { return mensaje; }
  public void setMensaje(String mensaje) { this.mensaje = mensaje; }
  public String getEstado() { return estado; }
  public void setEstado(String estado) { this.estado = estado; }
  public Instant getFechaCreacion() { return fechaCreacion; }
  public void setFechaCreacion(Instant fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
