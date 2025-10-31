
package com.miproyecto.Proyecto_Integrador.repository;






import com.miproyecto.Proyecto_Integrador.model.PedidoDetalle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface PedidoDetalleRepo extends JpaRepository<PedidoDetalle, Long> {
  @Query("select d from PedidoDetalle d where d.pedido.idPedido = :pedidoId")
  List<PedidoDetalle> findByPedidoId(@Param("pedidoId") Long pedidoId);
}