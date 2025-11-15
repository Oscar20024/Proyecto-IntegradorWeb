
package com.miproyecto.Proyecto_Integrador.repository;


import com.miproyecto.Proyecto_Integrador.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventoRepo extends JpaRepository<Evento, Long> {
    List<Evento> findByIdClienteOrderByIdEventoDesc(Long idCliente);
}
