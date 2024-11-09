package com.project.maisbahia.controllers;

import com.project.maisbahia.controllers.dtos.requests.UsuarioRequestRecord;
import com.project.maisbahia.controllers.dtos.responses.UsuarioResponseRecord;
import com.project.maisbahia.entities.usuarios.Usuario;
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
    //@PreAuthorize("hasAuthority('SCOPE_GERENTE')")
    public ResponseEntity<List<UsuarioResponseRecord>> buscarTodosUsuarios(){
        List<Usuario> listaUsuarios = usuarioService.buscarUsuarios();
        List<UsuarioResponseRecord> listaUsuarioResponsRecords = new ArrayList<>();

        for (Usuario usuario : listaUsuarios) {
            listaUsuarioResponsRecords.add(new UsuarioResponseRecord(usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getPerfis()));
        }

        return ResponseEntity.ok().body(listaUsuarioResponsRecords);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseRecord> buscarUsuarioPorId(@PathVariable UUID id){
        Usuario usuario = usuarioService.buscarUsuario(id);
        return ResponseEntity.ok().body(new UsuarioResponseRecord(usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getPerfis()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_Gerente')")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id){
        Usuario usuario = usuarioService.buscarUsuario(id);
        usuarioService.deletarUsuario(usuario.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin")
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_Gerente')")
    public ResponseEntity<Void> criarUsuarioAdmin(@RequestBody UsuarioRequestRecord usuarioRequestRecord){

        Usuario novoUsuario = usuarioService.criarUsuarioAdmin(usuarioRequestRecord);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(novoUsuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_Gerente')")
    public ResponseEntity<Void> atualizarUsuario(@RequestBody UsuarioRequestRecord usuarioRequestRecord, @PathVariable UUID id){
        usuarioService.atualizarSenha(usuarioRequestRecord, id);
        return ResponseEntity.noContent().build();
    }

}
