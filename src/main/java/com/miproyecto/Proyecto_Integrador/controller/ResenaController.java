
package com.miproyecto.Proyecto_Integrador.controller;

import com.miproyecto.Proyecto_Integrador.model.Producto;
import com.miproyecto.Proyecto_Integrador.model.Resena;
import com.miproyecto.Proyecto_Integrador.repository.ProductoRepo;
import com.miproyecto.Proyecto_Integrador.repository.PedidoDetalleRepo;
import com.miproyecto.Proyecto_Integrador.repository.ResenaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/api/resenas")
@RequiredArgsConstructor
public class ResenaController {

    private final ResenaRepo resenaRepo;
    private final ProductoRepo productoRepo;
    private final PedidoDetalleRepo detalleRepo;

    // Productos de pedidos ENTREGADO del cliente
    @GetMapping("/opciones")
    public List<Map<String, Object>> productosParaResena(
            @RequestParam("clienteId") Long clienteId) {

        List<Producto> productos =
                productoRepo.findProductosEntregadosDeCliente(clienteId, "ENTREGADO");

        List<Map<String, Object>> lista = new ArrayList<>();
        for (Producto p : productos) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("idProducto", p.getIdProducto());
            m.put("nombre", p.getNombre());
            lista.add(m);
        }
        return lista;
    }

    // Crear reseña
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> body) {
        Long idCliente = Long.valueOf(body.get("idCliente").toString());
        Long idProducto = Long.valueOf(body.get("idProducto").toString());
        int puntuacion = Integer.parseInt(body.get("puntuacion").toString());
        String comentario = Objects.toString(body.get("comentario"), "");

        boolean ok = detalleRepo
                .existsByPedido_IdClienteAndPedido_EstadoAndIdProducto(
                        idCliente, "ENTREGADO", idProducto);

        if (!ok) {
            return ResponseEntity.badRequest().body(
                    Map.of("ok", false,
                           "message", "Solo puedes reseñar productos de pedidos ENTREGADO de tu cuenta"));
        }

        Resena r = new Resena();
        r.setIdCliente(idCliente);
        r.setIdProducto(idProducto);
        r.setPuntuacion((short) puntuacion);
        r.setComentario(comentario);
        r.setFecha(Instant.now());
        resenaRepo.save(r);

        return ResponseEntity.ok(Map.of("ok", true));
    }
}
