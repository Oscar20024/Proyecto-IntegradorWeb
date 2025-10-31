
package com.miproyecto.Proyecto_Integrador.model;



import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_producto")
  private Long idProducto;

  private String nombre;

  private BigDecimal precio;

  private Integer stock;

  @Column(name = "imagen_url")
  private String imagenUrl;

  @Column(name = "id_categoria")
  private Long idCategoria;

  // Getters y Setters
  public Long getIdProducto() { return idProducto; }
  public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }

  public BigDecimal getPrecio() { return precio; }
  public void setPrecio(BigDecimal precio) { this.precio = precio; }

  public Integer getStock() { return stock; }
  public void setStock(Integer stock) { this.stock = stock; }

  public String getImagenUrl() { return imagenUrl; }
  public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

  public Long getIdCategoria() { return idCategoria; }
  public void setIdCategoria(Long idCategoria) { this.idCategoria = idCategoria; }
}
