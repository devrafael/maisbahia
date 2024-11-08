package com.project.maisbahia.services;


import com.project.maisbahia.controllers.dtos.requests.AutenticacaoRequest;
import com.project.maisbahia.controllers.dtos.requests.UsuarioRequest;
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

    public Optional<Usuario> buscarPorLogin(AutenticacaoRequest authRequest){
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

    public Usuario criarUsuarioAdmin(UsuarioRequest usuarioRequest) {
        Optional<Usuario> usuarioJaExiste = usuarioRepository.findByLogin(usuarioRequest.login());

        if (usuarioJaExiste.isPresent()) {
            throw new DataIntegrityViolationException("Já existe um usuário com essas credenciais!");
        }

        var senhaHash = passwordEncoder.encode(usuarioRequest.senha());
        var roleUser = perfilRepository.findByNome(Perfil.Values.GERENTE.name());

        Usuario u = new Usuario();
        u.setLogin(usuarioRequest.login());
        u.setSenha(senhaHash);
        u.setNome(usuarioRequest.nome());
        u.setPerfis(Set.of(roleUser));
        return usuarioRepository.save(u);
    }

    public Usuario atualizarUsuario(UsuarioRequest usuarioRequest, UUID id) {
        Usuario usuarioAtualizado = buscarUsuario(id);
        var senhaHash = "";

        if(usuarioRequest.nome() == "" || usuarioRequest.senha() == ""){
            throw new EmptyCredentialsException("Não foi possivel atualizar, pois alguma credencial pode estar vazia!");
        } else if (usuarioRequest.nome() == null && usuarioRequest.senha() != null) {
            senhaHash = passwordEncoder.encode(usuarioRequest.senha());
            usuarioAtualizado.setNome(usuarioAtualizado.getNome());
            usuarioAtualizado.setSenha(senhaHash);
        } else if (usuarioRequest.nome() != null && usuarioRequest.senha() == null) {
            usuarioAtualizado.setSenha(usuarioAtualizado.getSenha());
            usuarioAtualizado.setNome(usuarioRequest.nome());
        } else if (usuarioRequest.nome() != null) {
            senhaHash = passwordEncoder.encode(usuarioRequest.senha());
            usuarioAtualizado.setNome(usuarioRequest.nome());
            usuarioAtualizado.setSenha(senhaHash);
        }

        usuarioRepository.save(usuarioAtualizado);
        return usuarioAtualizado;
    }



}