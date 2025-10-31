/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miproyecto.Proyecto_Integrador.controller;

import com.miproyecto.Proyecto_Integrador.model.Contacto;
import com.miproyecto.Proyecto_Integrador.repository.ContactoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/contactos")
@RequiredArgsConstructor
public class ContactoController {

  private final ContactoRepo repo;

  @PostMapping
  public ResponseEntity<?> crear(@RequestBody Map<String, Object> body) {
    try {
      Contacto c = new Contacto();

      Object id = body.get("idCliente");
      if (id != null && !id.toString().isBlank()) {
        c.setIdCliente(Long.valueOf(id.toString()));
      }

      String nombre = Objects.toString(body.get("nombre"), "").trim();
      if (nombre.isEmpty()) {
        return ResponseEntity.badRequest().body(Map.of("error","El nombre es obligatorio"));
      }

      c.setNombre(nombre);
      c.setTelefono(Objects.toString(body.get("telefono"), null));
      c.setEmail(Objects.toString(body.get("email"), null));
      c.setMensaje(Objects.toString(body.get("mensaje"), null));
      c.setEstado(Objects.toString(body.getOrDefault("estado", "EN_REVISION")));

      var saved = repo.save(c);
      return ResponseEntity.ok(Map.of("idContacto", saved.getIdContacto()));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }
}