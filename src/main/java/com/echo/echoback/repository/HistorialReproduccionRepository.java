package com.echo.echoback.repository;

import com.echo.echoback.domain.HistorialReproduccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialReproduccionRepository extends JpaRepository<HistorialReproduccion, Long> {
    List<HistorialReproduccion> findByUsuarioId(Long usuarioId);
}
