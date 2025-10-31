
package com.miproyecto.Proyecto_Integrador.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cliente")
    private Integer idCliente;

    @NotBlank @Size(max=100)
    private String nombre;

    @Size(max=100)
    private String apellido;

    @NotBlank @Email @Size(max=100)
    @Column(unique = true)
    private String email;

    @NotBlank @Size(max=255)
    private String contrasena; // HASH (BCrypt)

    @Size(max=20)
    private String telefono;

    @Column(name="fecha_registro", columnDefinition="timestamp")
    private Instant fechaRegistro = Instant.now();

    private LocalDate fechaCumple;

    // getters/setters
    public Integer getIdCliente() { return idCliente; }
    public String getNombre() { return nombre; }
    public void setNombre(String v) { this.nombre = v; }
    public String getApellido() { return apellido; }
    public void setApellido(String v) { this.apellido = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String v) { this.contrasena = v; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String v) { this.telefono = v; }
    public Instant getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Instant v) { this.fechaRegistro = v; }
    public LocalDate getFechaCumple() { return fechaCumple; }
    public void setFechaCumple(LocalDate v) { this.fechaCumple = v; }
}
