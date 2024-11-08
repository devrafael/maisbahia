package com.project.maisbahia.controllers;

import com.project.maisbahia.controllers.dtos.requests.UsuarioRequest;
import com.project.maisbahia.controllers.dtos.responses.UsuarioResponse;
import com.project.maisbahia.entities.Usuario;
import com.project.maisbahia.services.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('SCOPE_GERENTE')")
    public ResponseEntity<List<UsuarioResponse>> buscarTodosUsuarios(){
        List<Usuario> listaUsuarios = usuarioService.buscarUsuarios();
        List<UsuarioResponse> listaUsuarioResponses = new ArrayList<>();

        for (Usuario usuario : listaUsuarios) {
            listaUsuarioResponses.add(new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getPerfis()));
        }

        return ResponseEntity.ok().body(listaUsuarioResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarUsuarioPorId(@PathVariable UUID id){
        Usuario usuario = usuarioService.buscarUsuario(id);
        return ResponseEntity.ok().body(new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getPerfis()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_GERENTE')")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id){
        Usuario usuario = usuarioService.buscarUsuario(id);
        usuarioService.deletarUsuario(usuario.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin")
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_GERENTE')")
    public ResponseEntity<Void> criarUsuarioAdmin(@RequestBody UsuarioRequest usuarioRequest){
        Usuario novoUsuario = usuarioService.criarUsuarioAdmin(usuarioRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(novoUsuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_GERENTE')")
    public ResponseEntity<Void> atualizarUsuario(@RequestBody UsuarioRequest usuarioRequest, @PathVariable UUID id){
        usuarioService.atualizarUsuario(usuarioRequest, id);
        return ResponseEntity.noContent().build();
    }

}
