package com.echo.echoback.service;

import com.echo.echoback.domain.MeGusta;
import com.echo.echoback.domain.Usuario;
import com.echo.echoback.domain.Cancion;
import com.echo.echoback.domain.Video;
import com.echo.echoback.repository.MeGustaRepository;
import com.echo.echoback.repository.UsuarioRepository;
import com.echo.echoback.repository.CancionRepository;
import com.echo.echoback.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeGustaService {

    @Autowired
    private MeGustaRepository meGustaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CancionRepository cancionRepository;

    @Autowired
    private VideoRepository videoRepository;

    // Dar o quitar "Me Gusta"
    public boolean toggleMeGusta(Long usuarioId, Long cancionId, Long videoId) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("El usuario no existe");
        }

        Optional<MeGusta> existente = meGustaRepository.findByUsuarioIdAndCancionIdAndVideoId(usuarioId, cancionId, videoId);
        if (existente.isPresent()) {
            meGustaRepository.delete(existente.get());
            return false; // Se eliminó el "Me Gusta"
        }

        MeGusta meGusta = new MeGusta();
        meGusta.setUsuario(usuarioOptional.get());

        if (cancionId != null) {
            Cancion cancion = cancionRepository.findById(cancionId)
                    .orElseThrow(() -> new RuntimeException("Canción no encontrada"));
            meGusta.setCancion(cancion);
        }

        if (videoId != null) {
            Video video = videoRepository.findById(videoId)
                    .orElseThrow(() -> new RuntimeException("Video no encontrado"));
            meGusta.setVideo(video);
        }

        meGustaRepository.save(meGusta);
        return true; // Se agregó el "Me Gusta"
    }

    // Obtener los "Me Gusta" de un usuario
    public List<MeGusta> obtenerMeGustaPorUsuario(Long usuarioId) {
        return meGustaRepository.findByUsuarioId(usuarioId);
    }

    // Contar cuántos "Me Gusta" tiene una canción o video
    public Long contarMeGusta(Long cancionId, Long videoId) {
        return meGustaRepository.countByCancionIdAndVideoId(cancionId, videoId);
    }

    // Eliminar un "Me Gusta"
    public void eliminarMeGusta(Long id) {
        meGustaRepository.deleteById(id);
    }
}
