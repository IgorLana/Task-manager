package com.tarefas.controller;

import com.tarefas.dto.JwtResponseDTO;
import com.tarefas.dto.LoginRequestDTO;
import com.tarefas.dto.RegistroRequest;
import com.tarefas.entity.UsuarioEntity;
import com.tarefas.repository.UsuarioRepository;
import com.tarefas.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager,
                          UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.senha());
        authenticationManager.authenticate(authToken);

        var usuario = usuarioRepository.findByUsername(dto.username()).orElseThrow();
        var token = jwtService.generateToken(usuario.getUsername());
        return ResponseEntity.ok(new JwtResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registrar(@RequestBody RegistroRequest request) {
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Email já está em uso.");
        }

        var usuario = new UsuarioEntity();
        usuario.setNome(request.getNome());
        usuario.setUsername(request.getUsername());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setRole("USER");

        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(usuario.getUsername());
        return ResponseEntity.ok(token);
    }

}
