package com.echo.echoback.repository;

import com.echo.echoback.domain.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByUsuarioId(Long usuarioId);
    List<Comentario> findByCancionIdOrVideoId(Long cancionId, Long videoId);
}
