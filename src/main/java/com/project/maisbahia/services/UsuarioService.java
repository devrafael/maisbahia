package com.project.maisbahia.services;


import com.project.maisbahia.controllers.dtos.requests.AutenticacaoRequestRecord;
import com.project.maisbahia.controllers.dtos.requests.UsuarioRequestRecord;
import com.project.maisbahia.entities.Perfil;
import com.project.maisbahia.entities.Usuario;
import com.project.maisbahia.repositories.PerfilRepository;
import com.project.maisbahia.repositories.UsuarioRepository;
import com.project.maisbahia.services.exceptions.EmptyCredentialsException;
import com.project.maisbahia.services.exceptions.IncorrectCredentialsException;
import com.project.maisbahia.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<Usuario> buscarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    public Usuario buscarUsuario(UUID id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado na base de dados! Id: " + id));
    }

    public Optional<Usuario> buscarPorLogin(AutenticacaoRequestRecord authRequest){
        var usuario = usuarioRepository.findByLogin(authRequest.login());

        if (usuario.isEmpty() || !(usuario.get().LoginCorreto(authRequest, passwordEncoder))) {
            throw new IncorrectCredentialsException("Credenciais incorretas! Favor verificar os dados de login.");
        }
        return usuario;
    }

    public void deletarUsuario(UUID id) {
        Usuario u = buscarUsuario(id);


        usuarioRepository.delete(u);
    }

    public Usuario criarUsuarioAdmin(UsuarioRequestRecord usuarioRequestRecord) {
        Optional<Usuario> usuarioJaExiste = usuarioRepository.findByLogin(usuarioRequestRecord.login());

        if (usuarioJaExiste.isPresent()) {
            throw new DataIntegrityViolationException("Já existe um usuário com essas credenciais!");
        }

        var senhaHash = passwordEncoder.encode(usuarioRequestRecord.senha());
        var roleUser = perfilRepository.findByNome(Perfil.Values.GERENTE.name());

        Usuario u = new Usuario();
        u.setLogin(usuarioRequestRecord.login());
        u.setSenha(senhaHash);
        u.setNome(usuarioRequestRecord.nome());
        u.setPerfis(Set.of(roleUser));
        return usuarioRepository.save(u);
    }

    public Usuario atualizarUsuario(UsuarioRequestRecord usuarioRequestRecord, UUID id) {
        Usuario usuarioAtualizado = buscarUsuario(id);
        var senhaHash = "";

        if(usuarioRequestRecord.nome() == "" || usuarioRequestRecord.senha() == ""){
            throw new EmptyCredentialsException("Não foi possivel atualizar, pois alguma credencial pode estar vazia!");
        } else if (usuarioRequestRecord.nome() == null && usuarioRequestRecord.senha() != null) {
            senhaHash = passwordEncoder.encode(usuarioRequestRecord.senha());
            usuarioAtualizado.setNome(usuarioAtualizado.getNome());
            usuarioAtualizado.setSenha(senhaHash);
        } else if (usuarioRequestRecord.nome() != null && usuarioRequestRecord.senha() == null) {
            usuarioAtualizado.setSenha(usuarioAtualizado.getSenha());
            usuarioAtualizado.setNome(usuarioRequestRecord.nome());
        } else if (usuarioRequestRecord.nome() != null) {
            senhaHash = passwordEncoder.encode(usuarioRequestRecord.senha());
            usuarioAtualizado.setNome(usuarioRequestRecord.nome());
            usuarioAtualizado.setSenha(senhaHash);
        }

        usuarioRepository.save(usuarioAtualizado);
        return usuarioAtualizado;
    }



}