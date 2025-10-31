
package com.miproyecto.Proyecto_Integrador.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.miproyecto.Proyecto_Integrador.controller.dto.RegistroDTO;
import com.miproyecto.Proyecto_Integrador.model.Cliente;
import com.miproyecto.Proyecto_Integrador.service.ClienteService;

import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*") // útil si sirves el HTML desde otro puerto/origen
public class ClienteController {

    private final ClienteService service;
    public ClienteController(ClienteService service) { this.service = service; }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistroDTO dto) {
        Cliente c = service.registrar(dto);
        return ResponseEntity.status(201).body(
            Map.of("id", c.getIdCliente(), "email", c.getEmail())
        );
    }
}