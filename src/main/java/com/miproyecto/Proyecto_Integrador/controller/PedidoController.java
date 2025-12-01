package com.miproyecto.Proyecto_Integrador.controller;

import com.miproyecto.Proyecto_Integrador.controller.dto.DetalleDTO;
import com.miproyecto.Proyecto_Integrador.controller.dto.PedidoDTO;
import com.miproyecto.Proyecto_Integrador.model.Cliente;
import com.miproyecto.Proyecto_Integrador.model.Pedido;
import com.miproyecto.Proyecto_Integrador.model.PedidoDetalle;
import com.miproyecto.Proyecto_Integrador.model.PuntosMovimiento;
import com.miproyecto.Proyecto_Integrador.repository.ClienteRepo;
import com.miproyecto.Proyecto_Integrador.repository.PedidoDetalleRepo;
import com.miproyecto.Proyecto_Integrador.repository.PedidoRepo;
import com.miproyecto.Proyecto_Integrador.repository.PuntosMovimientoRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.miproyecto.Proyecto_Integrador.repository.ClienteRepo;
import com.miproyecto.Proyecto_Integrador.repository.PuntosMovimientoRepo;

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
    private final ClienteRepo clienteRepo;             // 👈 nuevo
    private final PuntosMovimientoRepo puntosRepo;     // 👈 nuevo

    @GetMapping
    public ResponseEntity<?> listarPorCliente(@RequestParam("clienteId") Long clienteId) {
        var lista = pedidoRepo.findByIdClienteOrderByIdPedidoDesc(clienteId)
                .stream()
                .map(p -> new PedidoDTO(
                        p.getIdPedido(),
                        p.getFechaPedido() != null ? p.getFechaPedido().toString() : null,
                        p.getFechaEntrega() != null ? p.getFechaEntrega().toString() : "—",
                        p.getHoraEntrega() != null ? p.getHoraEntrega().toString() : "",
                        p.getEstado(), p.getTotal(),
                        p.getDistrito(), p.getDireccion(), p.getReferencia(),
                        p.getComentarios()
                ))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody Map<String, Object> body) {
        try {
            var p = new Pedido();
            p.setIdCliente(Long.valueOf(body.get("idCliente").toString()));
            p.setEstado(body.getOrDefault("estado", "EN_REVISION").toString());
            p.setTotal(new BigDecimal(body.getOrDefault("total", "0").toString()));

            Object f = body.get("fechaEntrega");
            if (f != null && !f.toString().isBlank()) p.setFechaEntrega(LocalDate.parse(f.toString()));

            Object h = body.get("horaEntrega");
            if (h != null && !h.toString().isBlank()) p.setHoraEntrega(LocalTime.parse(h.toString()));

            p.setDistrito(Objects.toString(body.get("distrito"), null));
            p.setDireccion(Objects.toString(body.get("direccion"), null));
            p.setReferencia(Objects.toString(body.get("referencia"), null));
            p.setComentarios(Objects.toString(body.get("comentarios"), null));

            var saved = pedidoRepo.save(p);
            return ResponseEntity.ok(Map.of("idPedido", saved.getIdPedido()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{idPedido}/detalles")
    @Transactional
    public ResponseEntity<?> crearDetalle(@PathVariable Long idPedido,
                                          @RequestBody DetalleDTO dto) {
        var pedido = pedidoRepo.findById(idPedido)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));

        if (dto.idProducto() == null)
            return ResponseEntity.badRequest().body(Map.of("error", "idProducto es obligatorio (tu tabla lo exige)"));
        if (dto.cantidad() == null || dto.cantidad() <= 0)
            return ResponseEntity.badRequest().body(Map.of("error", "cantidad inválida"));
        if (dto.precioUnitario() == null)
            return ResponseEntity.badRequest().body(Map.of("error", "precioUnitario es obligatorio"));

        var det = new PedidoDetalle();
        det.setPedido(pedido);
        det.setIdProducto(dto.idProducto());
        det.setCantidad(dto.cantidad());
        det.setPrecioUnitario(dto.precioUnitario());
        det.setNombreProducto(dto.nombreProducto());

        var saved = detalleRepo.save(det);
        return ResponseEntity.ok(Map.of("idDetalle", saved.getIdDetalle()));
    }

    // 👉 marcar pedido como ENTREGADO + generar puntos
    @PostMapping("/{idPedido}/entregar")
    @Transactional
    public ResponseEntity<?> marcarPedidoEntregado(@PathVariable Long idPedido) {
        Pedido pedido = pedidoRepo.findById(idPedido)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));

        pedido.setEstado("ENTREGADO");
        pedidoRepo.save(pedido);

        generarPuntosPorPedido(pedido);

        return ResponseEntity.ok(Map.of(
                "mensaje", "Pedido marcado como ENTREGADO y puntos generados"
        ));
    }

private void generarPuntosPorPedido(Pedido pedido) {

    // 1. Buscar cliente (si el pedido tiene idCliente)
    Cliente cliente = null;
    if (pedido.getIdCliente() != null) {
        cliente = clienteRepo.findById(pedido.getIdCliente())
                             .orElse(null);
    }

    // 2. Obtener detalles del pedido
    List<PedidoDetalle> detalles =
            detalleRepo.findByPedidoId(pedido.getIdPedido());

    // 3. Crear un movimiento por cada detalle
    for (PedidoDetalle d : detalles) {

        int puntos = d.getCantidad() * 10; // regla simple

        PuntosMovimiento mov = new PuntosMovimiento();
        mov.setCliente(cliente);
        mov.setPedido(pedido);
        mov.setPedidoDetalle(d);
        mov.setProductoId(d.getIdProducto());   // o setProducto(...) si usas relación
        mov.setTipo("GANADO");
        mov.setPuntos(puntos);
        mov.setDescripcion("Compra de " + d.getNombreProducto());

        puntosRepo.save(mov);
    }
}


}
