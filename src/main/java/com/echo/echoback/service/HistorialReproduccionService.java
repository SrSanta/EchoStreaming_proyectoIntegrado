package com.echo.echoback.service;

import com.echo.echoback.domain.HistorialReproduccion;
import com.echo.echoback.domain.Usuario;
import com.echo.echoback.domain.Cancion;
import com.echo.echoback.domain.Video;
import com.echo.echoback.repository.HistorialReproduccionRepository;
import com.echo.echoback.repository.UsuarioRepository;
import com.echo.echoback.repository.CancionRepository;
import com.echo.echoback.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HistorialReproduccionService {

    @Autowired
    private HistorialReproduccionRepository historialReproduccionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CancionRepository cancionRepository;

    @Autowired
    private VideoRepository videoRepository;

    // Guardar una reproducción en el historial
    public HistorialReproduccion guardarReproduccion(Long usuarioId, Long cancionId, Long videoId) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("El usuario no existe");
        }

        HistorialReproduccion historial = new HistorialReproduccion();
        historial.setUsuario(usuarioOptional.get());
        historial.setFechaReproduccion(new Date());

        if (cancionId != null) {
            Cancion cancion = cancionRepository.findById(cancionId)
                    .orElseThrow(() -> new RuntimeException("Canción no encontrada"));
            historial.setCancion(cancion);
        }

        if (videoId != null) {
            Video video = videoRepository.findById(videoId)
                    .orElseThrow(() -> new RuntimeException("Video no encontrado"));
            historial.setVideo(video);
        }

        return historialReproduccionRepository.save(historial);
    }

    // Obtener el historial de un usuario
    public List<HistorialReproduccion> obtenerHistorialPorUsuario(Long usuarioId) {
        return historialReproduccionRepository.findByUsuarioId(usuarioId);
    }

    // Eliminar una entrada del historial
    public void eliminarHistorial(Long id) {
        historialReproduccionRepository.deleteById(id);
    }
}
