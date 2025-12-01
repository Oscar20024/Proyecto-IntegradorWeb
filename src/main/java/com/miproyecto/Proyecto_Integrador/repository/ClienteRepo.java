
package com.miproyecto.Proyecto_Integrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.miproyecto.Proyecto_Integrador.model.Cliente;
import java.util.Optional;

public interface ClienteRepo extends JpaRepository<Cliente, Long> {

    boolean existsByEmail(String email);
    Optional<Cliente> findByEmail(String email);

}