
package com.miproyecto.Proyecto_Integrador.controller;


import com.miproyecto.Proyecto_Integrador.model.Producto;
import com.miproyecto.Proyecto_Integrador.repository.ProductoRepo;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/public")
public class ProductoPublicController {

    private final ProductoRepo productoRepo;

    public ProductoPublicController(ProductoRepo productoRepo) {
        this.productoRepo = productoRepo;
    }

    // === ENDPOINT PARA STOCK (SIN TOKEN) ===
    @GetMapping("/stock/{id}")
    public ResponseEntity<Map<String, Object>> obtenerStockPublico(@PathVariable Long id) {
        Optional<Producto> prodOpt = productoRepo.findById(id);
        if (prodOpt.isEmpty()) return ResponseEntity.notFound().build();

        Producto prod = prodOpt.get();
        Map<String, Object> data = new HashMap<>();
        data.put("idProducto", prod.getIdProducto());
        data.put("nombre", prod.getNombre());
        data.put("stock", prod.getStock());
        return ResponseEntity.ok(data);
    }

    // === OPCIONAL: BUSCAR POR NOMBRE (SIN TOKEN) ===
    @GetMapping("/productos/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        List<Producto> encontrados = productoRepo.findByNombreContainingIgnoreCase(nombre);
        return ResponseEntity.ok(encontrados);
    }
}
