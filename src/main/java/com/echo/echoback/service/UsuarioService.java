package com.echo.echoback.service;

import com.echo.echoback.domain.Usuario;
import com.echo.echoback.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registrar un usuario con contraseña encriptada
    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setContrasenya(passwordEncoder.encode(usuario.getContrasenya()));
        return usuarioRepository.save(usuario);
    }

    // Obtener un usuario por ID
    public Usuario obtenerUsuarioPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null);
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Eliminar un usuario por ID
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Validar usuario y contraseña
    public Usuario validarUsuario(String email, String contraseña) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && passwordEncoder.matches(contraseña, usuario.getContrasenya())) {
            return usuario;
        }
        return null;
    }
}
