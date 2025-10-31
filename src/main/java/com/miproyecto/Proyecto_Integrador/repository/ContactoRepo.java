/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miproyecto.Proyecto_Integrador.repository;
import com.miproyecto.Proyecto_Integrador.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactoRepo extends JpaRepository<Contacto, Long> {}