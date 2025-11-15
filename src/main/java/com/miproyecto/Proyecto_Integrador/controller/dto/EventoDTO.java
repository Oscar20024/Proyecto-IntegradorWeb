
package com.miproyecto.Proyecto_Integrador.controller.dto;

public record EventoDTO(
  Long idEvento,
  String tipoEvento,
  String fechaCreacion,
  String fechaEvento,
  String horaEvento,
  String estado,
  String distrito,
  String direccion,
  String referencia,
  String comentarios,
  Integer invitados,
  String detalles
) {}

