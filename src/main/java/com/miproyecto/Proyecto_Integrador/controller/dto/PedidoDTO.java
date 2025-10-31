/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miproyecto.Proyecto_Integrador.controller.dto;




import java.math.BigDecimal;

// PedidoDTO.java  
public record PedidoDTO(
  Long idPedido, String fechaPedido, String fechaEntrega, String horaEntrega,
  String estado, BigDecimal total, String distrito, String direccion,
  String referencia, String comentarios   // 👈 nuevo
) {}
