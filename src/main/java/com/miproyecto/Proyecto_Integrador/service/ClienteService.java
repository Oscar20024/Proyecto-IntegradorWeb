
package com.miproyecto.Proyecto_Integrador.service;



import com.miproyecto.Proyecto_Integrador.repository.ClienteRepo;
import com.miproyecto.Proyecto_Integrador.model.Cliente;
import com.miproyecto.Proyecto_Integrador.controller.dto.RegistroDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ClienteService {

    private final ClienteRepo repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public ClienteService(ClienteRepo repo) { this.repo = repo; }

    public Cliente registrar(RegistroDTO d) {
        if (d.email() == null || d.email().isBlank())
            throw new IllegalArgumentException("Email obligatorio");
        if (repo.existsByEmail(d.email()))
            throw new IllegalArgumentException("Email ya registrado");

        Cliente c = new Cliente();
        c.setNombre(d.nombre());
        c.setApellido(d.apellido());
        c.setEmail(d.email());
        c.setContrasena(encoder.encode(d.contrasena())); // HASH
        c.setTelefono(d.telefono());
        c.setFechaRegistro(Instant.now());
        c.setFechaCumple(d.fecha_cumple());

        return repo.save(c);
    }
}
