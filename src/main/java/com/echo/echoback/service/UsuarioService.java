package com.echo.echoback.service;

import com.echo.echoback.domain.Usuario;
import com.echo.echoback.impl.Rol;
import com.echo.echoback.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

        // Asignamos el rol USER por defecto
        if (usuario.getRol() == null) {
            usuario.setRol(Rol.USER);
        }

        return usuarioRepository.save(usuario);
    }

    // Obtener un usuario por ID
    public Usuario obtenerUsuarioPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null);
    }

    // Obtener todos los usuarios
//    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Eliminar un usuario por ID
    @PreAuthorize("hasAuthority('ADMIN')")
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Validar usuario y contrasenya
    public Usuario validarUsuario(String email, String contrasenya) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && passwordEncoder.matches(contrasenya, usuario.getContrasenya())) {
            return usuario;
        }
        return null;
    }
}
