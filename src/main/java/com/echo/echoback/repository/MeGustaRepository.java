package com.echo.echoback.repository;

import com.echo.echoback.domain.MeGusta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeGustaRepository extends JpaRepository<MeGusta, Long> {
    List<MeGusta> findByUsuarioId(Long usuarioId);
    Long countByCancionIdAndVideoId(Long cancionId, Long videoId);
    Optional<MeGusta> findByUsuarioIdAndCancionIdAndVideoId(Long usuarioId, Long cancionId, Long videoId);
}
