
package com.miproyecto.Proyecto_Integrador.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/debug")
public class DebugController {
    @GetMapping("/whoami")
    public Map<String,Object> me(Authentication a){
        return Map.of(
            "name", a != null ? a.getName() : null,
            "roles", a != null ? a.getAuthorities().stream()
                               .map(GrantedAuthority::getAuthority)
                               .collect(Collectors.toList()) : null
        );
    }
}
