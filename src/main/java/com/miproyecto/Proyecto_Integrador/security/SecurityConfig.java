package com.miproyecto.Proyecto_Integrador.security;

import com.miproyecto.Proyecto_Integrador.repository.ClienteRepo;
import com.miproyecto.Proyecto_Integrador.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  PasswordEncoder encoder() { return new BCryptPasswordEncoder(); }

  @Bean
  SecurityFilterChain filter(HttpSecurity http, JwtService jwtService, ClienteRepo clienteRepo) throws Exception {
    var jwtFilter = new JwtAuthFilter(jwtService, clienteRepo);

    http
      .csrf(cs -> cs.disable())
      .cors(cors -> {})
      .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(a -> a
        // Público
        .requestMatchers("/api/public/**").permitAll()
        .requestMatchers("/", "/index.html","/inicio.html","/login.html",
          "/registro.html","/carrito.html","/nosotros.html","/catalogo.html",
          "/eventos.html","/contacto.html","/foro.html","/reseñas.html","/puntos.html").permitAll()
        .requestMatchers("/css/**","/favicon.ico","/js/**","/img/**","/fonts/**","/files/**").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/clientes/register", "/api/contactos").permitAll()
        .requestMatchers(HttpMethod.GET , "/api/debug/**", "/api/productos/**").permitAll()
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

        // Requieren rol USER
        .requestMatchers(HttpMethod.POST, "/api/pedidos/**", "/api/pagos").hasRole("USER")
        .requestMatchers(HttpMethod.GET , "/api/pedidos/**", "/api/pagos").hasRole("USER")

        // Todo lo demás autenticado
        .anyRequest().authenticated()
      )
      .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    var c = new CorsConfiguration();
    c.setAllowedOrigins(List.of(
      "http://localhost:8080","http://127.0.0.1:8080",
      "http://localhost:3000","http://127.0.0.1:3000",
      "http://localhost:5173","http://localhost:5500"
    ));
    c.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
    c.setAllowedHeaders(List.of("Content-Type","Authorization"));
    c.setExposedHeaders(List.of("Location","Content-Disposition"));
    c.setAllowCredentials(true);
    c.setMaxAge(3600L);
    var s = new UrlBasedCorsConfigurationSource();
    s.registerCorsConfiguration("/**", c);
    return s;
  }
}
