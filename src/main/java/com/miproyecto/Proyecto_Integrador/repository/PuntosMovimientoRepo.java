/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miproyecto.Proyecto_Integrador.repository;

import com.miproyecto.Proyecto_Integrador.model.PuntosMovimiento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PuntosMovimientoRepo
        extends JpaRepository<PuntosMovimiento, Long> {

    // cliente.idCliente  → Cliente_IdCliente
    List<PuntosMovimiento> findByCliente_IdClienteOrderByFechaDesc(Long idCliente);
    // Si el id del Cliente se llama solo "id", sería:
    // List<PuntosMovimiento> findByCliente_IdOrderByFechaDesc(Long idCliente);
}