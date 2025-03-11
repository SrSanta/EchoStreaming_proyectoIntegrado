package com.echo.echoback.controller;


import com.echo.echoback.domain.Usuario;
import com.echo.echoback.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Registrar un nuevo usuario
    @PostMapping("/register")
    public Usuario registerUser(@RequestBody Usuario usuario) {
        return usuarioService.registrarUsuario(usuario);
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public Usuario getUserById(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    // Obtener todos los usuarios
    @GetMapping({"","/"})
    public List<Usuario> getAllUsers() {
        return usuarioService.obtenerTodosLosUsuarios();
    }

    // Eliminar un usuario por ID
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return "Usuario eliminado correctamente";
    }
}
