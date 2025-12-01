
package com.miproyecto.Proyecto_Integrador.repository;

import com.miproyecto.Proyecto_Integrador.model.ForoMensaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface ForoMensajeRepo extends JpaRepository<ForoMensaje, Long> {

    // últimos 50 mensajes (para cargar al entrar)
    List<ForoMensaje> findTop50ByOrderByIdMensajeAsc();

    // mensajes nuevos a partir de cierto id
    List<ForoMensaje> findByIdMensajeGreaterThanOrderByIdMensajeAsc(Long idMensaje);

    // para saber quién está "online" (actividad reciente)
    List<ForoMensaje> findByFechaAfter(Instant fecha);
}
