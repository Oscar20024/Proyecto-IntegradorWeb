
package com.miproyecto.Proyecto_Integrador.controller.dto;

import java.time.LocalDate;



public record RegistroDTO(
    String nombre,
    String apellido,
    String email,
    String contrasena,
    String telefono,
    LocalDate fecha_cumple
) {}
