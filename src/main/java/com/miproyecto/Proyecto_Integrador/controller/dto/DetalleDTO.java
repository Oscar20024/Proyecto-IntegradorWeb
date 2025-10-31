
package com.miproyecto.Proyecto_Integrador.controller.dto;

import java.math.BigDecimal;
public record DetalleDTO(
  Long idProducto,
  String nombreProducto,   // opcional
  Integer cantidad,
  BigDecimal precioUnitario
) {}
