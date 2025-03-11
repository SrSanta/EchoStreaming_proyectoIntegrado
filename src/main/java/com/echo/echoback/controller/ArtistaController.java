package com.echo.echoback.controller;

import com.echo.echoback.domain.Artista;
import com.echo.echoback.service.ArtistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artistas")
@CrossOrigin(origins = "http://localhost:4200")
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    // Registrar un artista
    @PostMapping("/register")
    public ResponseEntity<Artista> registerArtist(@RequestBody Artista artista) {
        Artista nuevoArtista = artistaService.registrarArtista(artista);
        return ResponseEntity.ok(nuevoArtista);
    }

    // Obtener un artista por ID
    @GetMapping("/{id}")
    public ResponseEntity<Artista> getArtistById(@PathVariable Long id) {
        return ResponseEntity.ok(artistaService.obtenerArtistaPorId(id));
    }

    // Listar todos los artistas
    @GetMapping
    public ResponseEntity<List<Artista>> getAllArtists() {
        return ResponseEntity.ok(artistaService.obtenerTodosLosArtistas());
    }

    // Eliminar un artista por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArtist(@PathVariable Long id) {
        artistaService.eliminarArtista(id);
        return ResponseEntity.ok("Artista eliminado correctamente");
    }
}
