package com.echo.echoback.controller;

import com.echo.echoback.domain.Genero;
import com.echo.echoback.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/generos")
@CrossOrigin(origins = "http://localhost:4200")
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    // Agregar un género
    @PostMapping("/agregar")
    public ResponseEntity<Genero> addGenre(@RequestBody Genero genero) {
        Genero nuevoGenero = generoService.agregarGenero(genero);
        return ResponseEntity.ok(nuevoGenero);
    }

    // Obtener todos los géneros
    @GetMapping
    public ResponseEntity<List<Genero>> getAllGenres() {
        return ResponseEntity.ok(generoService.obtenerTodosLosGeneros());
    }

    // Asignar géneros a una canción
    @PostMapping("/asignar")
    public ResponseEntity<String> assignGenresToSong(
            @RequestParam("cancionId") Long cancionId,
            @RequestParam("generoIds") List<Long> generoIds) {

        generoService.asignarGenerosACancion(cancionId, generoIds);
        return ResponseEntity.ok("Géneros asignados correctamente");
    }

    // Eliminar un género por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable Long id) {
        generoService.eliminarGenero(id);
        return ResponseEntity.ok("Género eliminado correctamente");
    }
}
