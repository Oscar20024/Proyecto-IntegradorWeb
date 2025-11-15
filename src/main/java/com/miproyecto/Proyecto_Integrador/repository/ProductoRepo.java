
package com.miproyecto.Proyecto_Integrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.miproyecto.Proyecto_Integrador.model.Producto;
import com.miproyecto.Proyecto_Integrador.model.Producto;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Lock;

public interface ProductoRepo extends JpaRepository<Producto, Long> {

  // Lista con filtros opcionales: activos y/o por categoría.
  @Query(value = """
      SELECT * 
      FROM productos 
      WHERE (:soloActivos = false OR estado = 'ACTIVO')
        AND (:idCategoria IS NULL OR id_categoria = :idCategoria)
      ORDER BY id_producto DESC
      """, nativeQuery = true)
  List<Producto> listarCatalogo(
      @Param("soloActivos") boolean soloActivos,
      @Param("idCategoria") Long idCategoria
          
  );
  List<Producto> findByNombreContainingIgnoreCase(String nombre);

 @Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("select p from Producto p where p.idProducto = :id")
Optional<Producto> findByIdForUpdate(Long id);
}
