
package com.miproyecto.Proyecto_Integrador.repository;


import com.miproyecto.Proyecto_Integrador.model.Pago;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;




public interface PagoRepo extends JpaRepository<Pago, Long> {
    
    // 🔹 Nuevo método: busca todos los pagos de un pedido específico
    List<Pago> findByPedido_IdPedido(Long idPedido);
}
