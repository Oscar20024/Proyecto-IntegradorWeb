package com.miproyecto.Proyecto_Integrador.service;

import com.miproyecto.Proyecto_Integrador.controller.dto.LoginRequest;
import com.miproyecto.Proyecto_Integrador.controller.dto.LoginResponse;
import com.miproyecto.Proyecto_Integrador.repository.ClienteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClienteRepo clienteRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest r) {
        var cli = clienteRepo.findByEmail(r.email())
                .orElseThrow(() -> new RuntimeException("Email o contraseña inválidos"));

        if (!encoder.matches(r.password(), cli.getContrasena())) {
            throw new RuntimeException("Email o contraseña inválidos");
        }

        String token = jwtService.createToken(cli.getEmail());
        return new LoginResponse(cli.getNombre(), token, cli.getIdCliente());
    }
}
