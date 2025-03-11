package com.echo.echoback.service;

import com.echo.echoback.domain.Video;
import com.echo.echoback.domain.Cancion;
import com.echo.echoback.repository.VideoRepository;
import com.echo.echoback.repository.CancionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {
    private static final String UPLOAD_DIR = "src/main/resources/uploads/"; // Carpeta donde se guardarán los MP4

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CancionRepository cancionRepository;

    // Guardar un video con archivo MP4
    public Video guardarVideo(String titulo, Long cancionId, MultipartFile archivoVideo) throws IOException {
        // Verificar si la canción existe
        Optional<Cancion> cancionOptional = cancionRepository.findById(cancionId);
        if (cancionOptional.isEmpty()) {
            throw new RuntimeException("La canción asociada no existe");
        }

        // Guardar archivo MP4 en el servidor
        String fileName = System.currentTimeMillis() + "_" + archivoVideo.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.copy(archivoVideo.getInputStream(), filePath);

        // Guardar información en la base de datos
        Video nuevoVideo = new Video();
        nuevoVideo.setTitulo(titulo);
        nuevoVideo.setCancion(cancionOptional.get());
        nuevoVideo.setArchivoVideo(fileName);
        return videoRepository.save(nuevoVideo);
    }

    // Obtener un video por ID
    public Video obtenerVideoPorId(Long id) {
        return videoRepository.findById(id).orElseThrow(() -> new RuntimeException("Video no encontrado"));
    }

    // Obtener todos los videos
    public List<Video> obtenerTodosLosVideos() {
        return videoRepository.findAll();
    }

    // Eliminar un video por ID
    public void eliminarVideo(Long id) {
        videoRepository.deleteById(id);
    }
}
