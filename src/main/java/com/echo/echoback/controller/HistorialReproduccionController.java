package com.echo.echoback.controller;

import com.echo.echoback.domain.HistorialReproduccion;
import com.echo.echoback.service.HistorialReproduccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
@CrossOrigin(origins = "http://localhost:4200")
public class HistorialReproduccionController {

    @Autowired
    private HistorialReproduccionService historialReproduccionService;

    // Guardar una reproducción
    @PostMapping("/guardar")
    public ResponseEntity<HistorialReproduccion> saveHistory(
            @RequestParam("usuarioId") Long usuarioId,
            @RequestParam(value = "cancionId", required = false) Long cancionId,
            @RequestParam(value = "videoId", required = false) Long videoId) {

        HistorialReproduccion historial = historialReproduccionService.guardarReproduccion(usuarioId, cancionId, videoId);
        return ResponseEntity.ok(historial);
    }

    // Obtener el historial de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<HistorialReproduccion>> getUserHistory(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(historialReproduccionService.obtenerHistorialPorUsuario(usuarioId));
    }

    // Eliminar una entrada del historial
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHistory(@PathVariable Long id) {
        historialReproduccionService.eliminarHistorial(id);
        return ResponseEntity.ok("Entrada eliminada del historial");
    }
}
