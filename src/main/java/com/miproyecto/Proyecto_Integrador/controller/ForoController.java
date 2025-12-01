
package com.miproyecto.Proyecto_Integrador.controller;

import com.miproyecto.Proyecto_Integrador.model.ForoMensaje;
import com.miproyecto.Proyecto_Integrador.repository.ForoMensajeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/foro")
@RequiredArgsConstructor
public class ForoController {

    private final ForoMensajeRepo foroMensajeRepo;

    // DTO simple para exponer datos
    record ForoMensajeDto(Long idMensaje, String alias, String contenido, Instant fecha) {}

    // GET /api/foro/mensajes?desdeId=123 (opcional)
    @GetMapping("/mensajes")
    public List<ForoMensajeDto> obtenerMensajes(@RequestParam(name = "desdeId", required = false) Long desdeId) {
        List<ForoMensaje> lista;
        if (desdeId != null && desdeId > 0) {
            lista = foroMensajeRepo.findByIdMensajeGreaterThanOrderByIdMensajeAsc(desdeId);
        } else {
            lista = foroMensajeRepo.findTop50ByOrderByIdMensajeAsc();
        }
        return lista.stream()
                .map(m -> new ForoMensajeDto(m.getIdMensaje(), m.getAlias(), m.getContenido(), m.getFecha()))
                .collect(Collectors.toList());
    }

    // POST /api/foro/mensajes   { alias, contenido }
    @PostMapping("/mensajes")
    public ResponseEntity<?> enviarMensaje(@RequestBody Map<String, String> body) {
        String alias = Optional.ofNullable(body.get("alias")).orElse("").trim();
        String contenido = Optional.ofNullable(body.get("contenido")).orElse("").trim();

        if (alias.isEmpty() || contenido.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "message", "Alias y contenido son obligatorios"));
        }

        if (alias.length() > 50) alias = alias.substring(0, 50);

        ForoMensaje msg = new ForoMensaje();
        msg.setAlias(alias);
        msg.setContenido(contenido);
        msg.setFecha(Instant.now());
        foroMensajeRepo.save(msg);

        return ResponseEntity.ok(Map.of("ok", true, "idMensaje", msg.getIdMensaje()));
    }

    // GET /api/foro/usuarios → quienes hablaron en últimos 5 minutos
    @GetMapping("/usuarios")
    public List<Map<String, String>> usuariosOnline() {
        Instant hace5 = Instant.now().minus(Duration.ofMinutes(5));
        List<ForoMensaje> recientes = foroMensajeRepo.findByFechaAfter(hace5);

        Map<String, Instant> ultimoMensaje = new HashMap<>();
        for (ForoMensaje m : recientes) {
            ultimoMensaje.merge(m.getAlias(), m.getFecha(),
                    (oldV, newV) -> newV.isAfter(oldV) ? newV : oldV);
        }

        return ultimoMensaje.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .map(e -> Map.of("alias", e.getKey()))
                .collect(Collectors.toList());
    }
}
