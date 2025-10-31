
package com.miproyecto.Proyecto_Integrador.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return " Spring Boot funciona correctamenteFDFSDFSFS!";
    }
}