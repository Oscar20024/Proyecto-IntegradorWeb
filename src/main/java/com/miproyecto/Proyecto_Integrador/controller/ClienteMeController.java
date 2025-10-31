package com.miproyecto.Proyecto_Integrador.controller;
import com.miproyecto.Proyecto_Integrador.model.Cliente;
import com.miproyecto.Proyecto_Integrador.repository.ClienteRepo;
import com.miproyecto.Proyecto_Integrador.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
public class ClienteMeController {

    
    private final JwtService jwtService;
    private final ClienteRepo clienteRepo;

    @GetMapping("/api/clientes/me")
    public ResponseEntity<?> me(@RequestHeader("Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }
        String token = authorization.substring(7);
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).build();
        }
        String email = jwtService.getSubject(token);
        Cliente c = clienteRepo.findByEmail(email).orElse(null);
        if (c == null) return ResponseEntity.status(404).build();

        return ResponseEntity.ok(new MeDTO(c.getIdCliente(), c.getNombre(), c.getEmail()));
    }

    record MeDTO(Integer idCliente, String nombre, String email) {}
}
