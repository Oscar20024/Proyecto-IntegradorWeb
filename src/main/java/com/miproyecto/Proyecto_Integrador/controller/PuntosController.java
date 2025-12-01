package com.miproyecto.Proyecto_Integrador.controller;

import com.miproyecto.Proyecto_Integrador.model.PuntosMovimiento;
import com.miproyecto.Proyecto_Integrador.repository.PuntosMovimientoRepo;
import com.miproyecto.Proyecto_Integrador.service.ClienteService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/puntos")
@RequiredArgsConstructor
public class PuntosController {

    private final PuntosMovimientoRepo puntosRepo;
    private final ClienteService clienteService;

    @GetMapping
    public Map<String, Object> misPuntos() {

        // 1) id del cliente logueado
        Long idCliente = clienteService.obtenerIdClienteLogueado();

        // 2) movimientos ordenados por fecha
        List<PuntosMovimiento> movimientos =
                puntosRepo.findByCliente_IdClienteOrderByFechaDesc(idCliente);

        int total = 0;
        List<Map<String,Object>> historial = new ArrayList<>();

        for (PuntosMovimiento m : movimientos) {

            int puntosCalc = "GANADO".equalsIgnoreCase(m.getTipo())
                    ? m.getPuntos()
                    : -m.getPuntos();

            total += puntosCalc;

            historial.add(Map.of(
                    "fecha",   m.getFecha().toLocalDate().toString(), // 👈 getFecha()
                    "detalle", m.getDescripcion(),
                    "puntos",  m.getPuntos(),
                    "tipo",    m.getTipo()
            ));
        }

        return Map.of(
                "total", total,
                "historial", historial
        );
    }
}
