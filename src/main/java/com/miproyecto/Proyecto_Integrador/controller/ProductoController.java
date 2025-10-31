
package com.miproyecto.Proyecto_Integrador.controller;


import com.miproyecto.Proyecto_Integrador.model.Producto;
import com.miproyecto.Proyecto_Integrador.repository.ProductoRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

  private final ProductoRepo repo;
  public ProductoController(ProductoRepo repo) { this.repo = repo; }

  // GET /api/productos?soloActivos=true&idCategoria=1
  @GetMapping
  public List<Producto> listar(
      @RequestParam(defaultValue = "true") boolean soloActivos,
      @RequestParam(required = false) Long idCategoria
  ){
    return repo.listarCatalogo(soloActivos, idCategoria);
  }
}
