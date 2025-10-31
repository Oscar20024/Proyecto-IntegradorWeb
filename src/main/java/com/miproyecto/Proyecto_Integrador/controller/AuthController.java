
package com.miproyecto.Proyecto_Integrador.controller;

import com.miproyecto.Proyecto_Integrador.controller.dto.*;
import com.miproyecto.Proyecto_Integrador.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService auth;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest r){
    return ResponseEntity.ok(auth.login(r));
  }
}
