package com.echo.echoback.controller;

import com.echo.echoback.domain.Usuario;
import com.echo.echoback.impl.Rol;
import com.echo.echoback.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true") // Permitir peticiones desde Angular
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    // Login: Verifica usuario y contraseña
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials, HttpSession session) {
        Usuario usuario = usuarioService.validarUsuario(credentials.get("email"), credentials.get("contraseña"));

        if (usuario != null) {
            session.setAttribute("usuarioId", usuario.getId()); // Guardamos el ID en la sesión

            Map<String, String> response = new HashMap<>();
            response.put("message", "Login exitoso");
            response.put("userId", usuario.getId().toString()); // Se enviará al frontend para guardarlo en LocalStorage
            return response;
        }

        throw new RuntimeException("Credenciales inválidas");
    }

    // Logout: Cierra sesión y elimina la sesión del usuario
    @PostMapping("/logout")
    public Map<String, String> logout(HttpSession session) {
        session.invalidate(); // Invalida la sesión
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout exitoso");
        return response;
    }

    // Verificar sesión activa
    @GetMapping("/check-session")
    public Map<String, Object> checkSession(HttpSession session) {
        Long usuarioId = (Long) session.getAttribute("usuarioId");

        Map<String, Object> response = new HashMap<>();
        if (usuarioId != null) {
            response.put("authenticated", true);
            response.put("userId", usuarioId);
        } else {
            response.put("authenticated", false);
        }
        return response;
    }

    @PostMapping("/register")
    public Usuario register(@RequestBody Usuario usuario) {
        // Solo los ADMIN pueden asignar roles diferentes a USER
        if (usuario.getRol() == Rol.ADMIN) {
            throw new RuntimeException("No puedes asignarte el rol ADMIN");
        }

        return usuarioService.registrarUsuario(usuario);
    }
}
