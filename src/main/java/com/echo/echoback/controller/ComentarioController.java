package com.echo.echoback.controller;

import com.echo.echoback.domain.Comentario;
import com.echo.echoback.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@CrossOrigin(origins = "http://localhost:4200")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    // Agregar un comentario a una canción o video
    @PostMapping("/agregar")
    public ResponseEntity<Comentario> addComment(
            @RequestParam("usuarioId") Long usuarioId,
            @RequestParam("contenido") String contenido,
            @RequestParam(value = "cancionId", required = false) Long cancionId,
            @RequestParam(value = "videoId", required = false) Long videoId) {

        Comentario comentario = comentarioService.agregarComentario(usuarioId, contenido, cancionId, videoId);
        return ResponseEntity.ok(comentario);
    }

    // Obtener los comentarios de una canción o video
    @GetMapping("/contenido")
    public ResponseEntity<List<Comentario>> getCommentsByContent(
            @RequestParam(value = "cancionId", required = false) Long cancionId,
            @RequestParam(value = "videoId", required = false) Long videoId) {

        return ResponseEntity.ok(comentarioService.obtenerComentariosPorContenido(cancionId, videoId));
    }

    // Obtener los comentarios de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Comentario>> getUserComments(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(comentarioService.obtenerComentariosPorUsuario(usuarioId));
    }

    // Eliminar un comentario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        comentarioService.eliminarComentario(id);
        return ResponseEntity.ok("Comentario eliminado correctamente");
    }
}