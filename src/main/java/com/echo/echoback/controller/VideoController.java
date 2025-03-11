package com.echo.echoback.controller;

import com.echo.echoback.domain.Video;
import com.echo.echoback.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/videos")
@CrossOrigin(origins = "http://localhost:4200")
public class VideoController {
    @Autowired
    private VideoService videoService;

    // Subir un video con un archivo MP4
    @PostMapping("/upload")
    public ResponseEntity<?> uploadVideo(
            @RequestParam("titulo") String titulo,
            @RequestParam("cancionId") Long cancionId,
            @RequestParam("archivoVideo") MultipartFile archivoVideo) {

        try {
            Video nuevoVideo = videoService.guardarVideo(titulo, cancionId, archivoVideo);
            return ResponseEntity.ok(nuevoVideo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir el archivo");
        }
    }

    // Obtener un video por ID
    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideoById(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.obtenerVideoPorId(id));
    }

    // Listar todos los videos
    @GetMapping
    public ResponseEntity<List<Video>> getAllVideos() {
        return ResponseEntity.ok(videoService.obtenerTodosLosVideos());
    }

    // Eliminar un video por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable Long id) {
        videoService.eliminarVideo(id);
        return ResponseEntity.ok("Video eliminado correctamente");
    }
}
