/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

