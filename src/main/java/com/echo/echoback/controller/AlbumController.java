package com.echo.echoback.controller;

import com.echo.echoback.domain.Album;
import com.echo.echoback.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albumes")
@CrossOrigin(origins = "http://localhost:4200")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    // Registrar un álbum
    @PostMapping("/register")
    public ResponseEntity<Album> registerAlbum(@RequestBody Album album) {
        Album nuevoAlbum = albumService.registrarAlbum(album);
        return ResponseEntity.ok(nuevoAlbum);
    }

    // Obtener un álbum por ID
    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.obtenerAlbumPorId(id));
    }

    // Listar todos los álbumes
    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        return ResponseEntity.ok(albumService.obtenerTodosLosAlbumes());
    }

    // Eliminar un álbum por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAlbum(@PathVariable Long id) {
        albumService.eliminarAlbum(id);
        return ResponseEntity.ok("Álbum eliminado correctamente");
    }
}
