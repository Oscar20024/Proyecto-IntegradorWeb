
package com.miproyecto.Proyecto_Integrador.controller;




import com.miproyecto.Proyecto_Integrador.model.Evento;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;


import com.miproyecto.Proyecto_Integrador.model.Evento;
import com.miproyecto.Proyecto_Integrador.repository.EventoRepo;
import com.miproyecto.Proyecto_Integrador.controller.dto.EventoDTO;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {
  private final EventoRepo eventoRepo;

  @GetMapping
  public ResponseEntity<?> listarPorCliente(@RequestParam("clienteId") Long clienteId) {
    var lista = eventoRepo.findByIdClienteOrderByIdEventoDesc(clienteId)
        .stream()
       .map(e -> new EventoDTO(
  e.getIdEvento(),
  e.getTipoEvento(),
  e.getFechaCreacion()!=null?e.getFechaCreacion().toString():null,
  e.getFechaEvento()!=null?e.getFechaEvento().toString():"—",
  e.getHoraEvento()!=null?e.getHoraEvento().toString():"",
  e.getEstado(),
  e.getDistrito(),
  e.getDireccion(),
  e.getReferencia(),
  e.getComentarios(),
  e.getInvitados(),
  e.getDetalles()
))

        .toList();
    return ResponseEntity.ok(lista);
  }

  @PostMapping
  public ResponseEntity<?> crearEvento(@RequestBody Map<String, Object> body){
    try {
      var e = new Evento();
      e.setIdCliente(Long.valueOf(body.get("idCliente").toString()));
      e.setEstado(Objects.toString(body.getOrDefault("estado", "EN_REVISION")));
      Object f = body.get("fechaEvento");
      if (f != null && !f.toString().isBlank()) e.setFechaEvento(LocalDate.parse(f.toString()));
      Object h = body.get("horaEvento");
      if (h != null && !h.toString().isBlank()) e.setHoraEvento(LocalTime.parse(h.toString()));
      e.setDistrito(Objects.toString(body.get("distrito"), null));
      e.setDireccion(Objects.toString(body.get("direccion"), null));
      e.setReferencia(Objects.toString(body.get("referencia"), null));
      e.setComentarios(Objects.toString(body.get("comentarios"), null));
      e.setTipoEvento(Objects.toString(body.get("tipo_evento"), null));
      Object inv = body.get("invitados");
if(inv!=null && !inv.toString().isBlank()) e.setInvitados(Integer.valueOf(inv.toString()));
e.setDetalles(Objects.toString(body.get("detalles"), null));


      var saved = eventoRepo.save(e);
      return ResponseEntity.ok(Map.of("idEvento", saved.getIdEvento()));
    } catch (Exception ex){
      ex.printStackTrace();
      return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }
  }
}
