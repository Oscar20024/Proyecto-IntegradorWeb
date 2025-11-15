
package com.miproyecto.Proyecto_Integrador.repository;


import com.miproyecto.Proyecto_Integrador.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.miproyecto.Proyecto_Integrador.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.miproyecto.Proyecto_Integrador.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepo extends JpaRepository<Pedido, Long> {
  List<Pedido> findByIdClienteOrderByIdPedidoDesc(Long idCliente);
}
