package com.echo.echoback.service;

import com.echo.echoback.domain.Comentario;
import com.echo.echoback.domain.Usuario;
import com.echo.echoback.domain.Cancion;
import com.echo.echoback.domain.Video;
import com.echo.echoback.repository.ComentarioRepository;
import com.echo.echoback.repository.UsuarioRepository;
import com.echo.echoback.repository.CancionRepository;
import com.echo.echoback.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CancionRepository cancionRepository;

    @Autowired
    private VideoRepository videoRepository;

    // Agregar un comentario
    public Comentario agregarComentario(Long usuarioId, String contenido, Long cancionId, Long videoId) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("El usuario no existe");
        }

        Comentario comentario = new Comentario();
        comentario.setUsuario(usuarioOptional.get());
        comentario.setContenido(contenido);
        comentario.setFechaComentario(new Date());

        if (cancionId != null) {
            Cancion cancion = cancionRepository.findById(cancionId)
                    .orElseThrow(() -> new RuntimeException("Canción no encontrada"));
            comentario.setCancion(cancion);
        }

        if (videoId != null) {
            Video video = videoRepository.findById(videoId)
                    .orElseThrow(() -> new RuntimeException("Video no encontrado"));
            comentario.setVideo(video);
        }

        return comentarioRepository.save(comentario);
    }

    // Obtener los comentarios de una canción o video
    public List<Comentario> obtenerComentariosPorContenido(Long cancionId, Long videoId) {
        return comentarioRepository.findByCancionIdOrVideoId(cancionId, videoId);
    }

    // Obtener los comentarios de un usuario
    public List<Comentario> obtenerComentariosPorUsuario(Long usuarioId) {
        return comentarioRepository.findByUsuarioId(usuarioId);
    }

    // Eliminar un comentario por ID
    @PreAuthorize("hasAuthority('ADMIN') or #comentarioRepository.findById(#id).get().usuario.id == authentication.principal.id")
    public void eliminarComentario(Long id) {
        comentarioRepository.deleteById(id);
    }
}
