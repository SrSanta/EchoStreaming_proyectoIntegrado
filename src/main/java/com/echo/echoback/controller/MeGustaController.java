package com.echo.echoback.controller;

import com.echo.echoback.domain.MeGusta;
import com.echo.echoback.service.MeGustaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/me-gusta")
@CrossOrigin(origins = "http://localhost:4200")
public class MeGustaController {
    @Autowired
    private MeGustaService meGustaService;

    // Dar "Me Gusta" a una canción o video
    @PostMapping("/toggle")
    public ResponseEntity<String> toggleLike(
            @RequestParam("usuarioId") Long usuarioId,
            @RequestParam(value = "cancionId", required = false) Long cancionId,
            @RequestParam(value = "videoId", required = false) Long videoId) {

        boolean liked = meGustaService.toggleMeGusta(usuarioId, cancionId, videoId);
        return ResponseEntity.ok(liked ? "Me Gusta agregado" : "Me Gusta eliminado");
    }

    // Obtener los "Me Gusta" de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MeGusta>> getUserLikes(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(meGustaService.obtenerMeGustaPorUsuario(usuarioId));
    }

    // Obtener cuántos "Me Gusta" tiene una canción o video
    @GetMapping("/count")
    public ResponseEntity<Long> getLikesCount(
            @RequestParam(value = "cancionId", required = false) Long cancionId,
            @RequestParam(value = "videoId", required = false) Long videoId) {

        return ResponseEntity.ok(meGustaService.contarMeGusta(cancionId, videoId));
    }

    // Eliminar un "Me Gusta"
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLike(@PathVariable Long id) {
        meGustaService.eliminarMeGusta(id);
        return ResponseEntity.ok("Me Gusta eliminado correctamente");
    }
}
