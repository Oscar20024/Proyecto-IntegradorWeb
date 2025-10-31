
package com.miproyecto.Proyecto_Integrador.controller;


import com.miproyecto.Proyecto_Integrador.model.Pedido;
import com.miproyecto.Proyecto_Integrador.model.PedidoDetalle;
import com.miproyecto.Proyecto_Integrador.repository.PedidoRepo;
import com.miproyecto.Proyecto_Integrador.repository.PedidoDetalleRepo;
import com.miproyecto.Proyecto_Integrador.controller.dto.PedidoDTO;
import com.miproyecto.Proyecto_Integrador.controller.dto.DetalleDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {
  private final PedidoRepo pedidoRepo;
  private final PedidoDetalleRepo detalleRepo;

@GetMapping
public ResponseEntity<?> listarPorCliente(@RequestParam("clienteId") Long clienteId) {
    var lista = pedidoRepo.findByIdClienteOrderByIdPedidoDesc(clienteId)
        .stream()
        // PedidoController.listarPorCliente(...)
.map(p -> new PedidoDTO(
  p.getIdPedido(),
  p.getFechaPedido()!=null ? p.getFechaPedido().toString():null,
  p.getFechaEntrega()!=null ? p.getFechaEntrega().toString():"—",
  p.getHoraEntrega()!=null ? p.getHoraEntrega().toString():"",
  p.getEstado(), p.getTotal(),
  p.getDistrito(), p.getDireccion(), p.getReferencia(),
  p.getComentarios()                    // 👈 nuevo
))

        .toList();

    return ResponseEntity.ok(lista);
}


  // POST /api/pedidos  (por si aún no lo tienes)
@PostMapping
public ResponseEntity<?> crearPedido(@RequestBody Map<String, Object> body){
  try {
    var p = new Pedido();
    p.setIdCliente(Long.valueOf(body.get("idCliente").toString()));
    p.setEstado(body.getOrDefault("estado", "EN_REVISION").toString());
    p.setTotal(new BigDecimal(body.getOrDefault("total","0").toString()));

    Object f = body.get("fechaEntrega");
    if (f != null && !f.toString().isBlank()) p.setFechaEntrega(LocalDate.parse(f.toString()));

    Object h = body.get("horaEntrega");
    if (h != null && !h.toString().isBlank()) p.setHoraEntrega(LocalTime.parse(h.toString()));

    // 👇 NUEVO: dirección
    p.setDistrito(  Objects.toString(body.get("distrito"),   null));
    p.setDireccion( Objects.toString(body.get("direccion"),  null));
    p.setReferencia(Objects.toString(body.get("referencia"), null));
// PedidoController.crearPedido(...)
p.setComentarios(Objects.toString(body.get("comentarios"), null));  

    var saved = pedidoRepo.save(p);
    return ResponseEntity.ok(Map.of("idPedido", saved.getIdPedido()));
  } catch (Exception e){
    return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
  }
}


  // POST /api/pedidos/{idPedido}/detalles
  @PostMapping("/{idPedido}/detalles")
  @Transactional
  public ResponseEntity<?> crearDetalle(@PathVariable Long idPedido,
                                        @RequestBody DetalleDTO dto){
    var pedido = pedidoRepo.findById(idPedido)
      .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));

    if (dto.idProducto() == null)
      return ResponseEntity.badRequest().body(Map.of("error","idProducto es obligatorio (tu tabla lo exige)"));
    if (dto.cantidad() == null || dto.cantidad() <= 0)
      return ResponseEntity.badRequest().body(Map.of("error","cantidad inválida"));
    if (dto.precioUnitario() == null)
      return ResponseEntity.badRequest().body(Map.of("error","precioUnitario es obligatorio"));

    var det = new PedidoDetalle();
    det.setPedido(pedido);
    det.setIdProducto(dto.idProducto());
    det.setCantidad(dto.cantidad());
    det.setPrecioUnitario(dto.precioUnitario());
    det.setNombreProducto(dto.nombreProducto()); // opcional

    var saved = detalleRepo.save(det);
    return ResponseEntity.ok(Map.of("idDetalle", saved.getIdDetalle()));
  }
}
