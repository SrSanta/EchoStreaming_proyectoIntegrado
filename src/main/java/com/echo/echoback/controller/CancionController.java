package com.echo.echoback.controller;

import com.echo.echoback.domain.Cancion;
import com.echo.echoback.service.CancionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/canciones")
@CrossOrigin(origins = "http://localhost:4200")
public class CancionController {

    @Autowired
    private CancionService cancionService;

    // Subir una canción con un archivo MP3
    @PostMapping("/upload")
    public ResponseEntity<?> uploadSong(
            @RequestParam("titulo") String titulo,
            @RequestParam("artistaId") Long artistaId,
            @RequestParam("archivoMp3") MultipartFile archivoMp3) {

        try {
            Cancion nuevaCancion = cancionService.guardarCancion(titulo, artistaId, archivoMp3);
            return ResponseEntity.ok(nuevaCancion);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir el archivo");
        }
    }

    // Obtener una canción por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cancion> getSongById(@PathVariable Long id) {
        return ResponseEntity.ok(cancionService.obtenerCancionPorId(id));
    }

    // Listar todas las canciones
    @GetMapping
    public ResponseEntity<List<Cancion>> getAllSongs() {
        return ResponseEntity.ok(cancionService.obtenerTodasLasCanciones());
    }

    // Eliminar una canción por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSong(@PathVariable Long id) {
        cancionService.eliminarCancion(id);
        return ResponseEntity.ok("Canción eliminada correctamente");
    }
}
