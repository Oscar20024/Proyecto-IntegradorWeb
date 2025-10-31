package com.miproyecto.Proyecto_Integrador.security;

import com.miproyecto.Proyecto_Integrador.model.Cliente;
import com.miproyecto.Proyecto_Integrador.repository.ClienteRepo;
import com.miproyecto.Proyecto_Integrador.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ClienteRepo clienteRepo;

    public JwtAuthFilter(JwtService jwtService, ClienteRepo clienteRepo) {
        this.jwtService = jwtService;
        this.clienteRepo = clienteRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {

        String auth = req.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        String token = auth.substring(7);
        if (!jwtService.validateToken(token)) {
            chain.doFilter(req, res);
            return;
        }

        String email = jwtService.getSubject(token);
        Cliente cli = clienteRepo.findByEmail(email).orElse(null);
        if (cli == null) {
            chain.doFilter(req, res);
            return;
        }

        var authToken = new UsernamePasswordAuthenticationToken(
                email, null, List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
        chain.doFilter(req, res);
    }
}
