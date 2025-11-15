
package com.miproyecto.Proyecto_Integrador.controller.dto;




import java.math.BigDecimal;

// PedidoDTO.java  
public record PedidoDTO(
  Long idPedido, String fechaPedido, String fechaEntrega, String horaEntrega,
  String estado, BigDecimal total, String distrito, String direccion,
  String referencia, String comentarios   // 👈 nuevo
) {}
