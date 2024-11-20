package com.project.maisbahia.services;


import com.project.maisbahia.controllers.records.requests.AutenticacaoRequestRecord;
import com.project.maisbahia.controllers.records.requests.UsuarioRequestRecord;
import com.project.maisbahia.entities.usuarios.Perfil;
import com.project.maisbahia.entities.usuarios.Usuario;
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
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuario(UUID id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado na base de dados! Id: " + id));
    }

    public Optional<Usuario> buscarPorLogin(AutenticacaoRequestRecord authRequest){
        var usuario = usuarioRepository.findByLogin(authRequest.login());

        if (usuario.isEmpty() || !(usuario.get().loginCorreto(authRequest, passwordEncoder))) {
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
        Set<Perfil> perfil = perfilRepository.findByNome(usuarioRequestRecord.perfilEnum());
        System.out.println(perfil);
        Usuario u = new Usuario();
        u.setLogin(usuarioRequestRecord.login());
        u.setSenha(senhaHash);
        u.setNome(usuarioRequestRecord.nome());
        u.setPerfis(perfil);

        u.setCargo(perfil.iterator().next().getNome().getNomeCargo());

        return usuarioRepository.save(u);
    }

    public Usuario atualizarSenha(UsuarioRequestRecord usuarioRequestRecord, UUID id) {
        Usuario usuarioAtualizado = buscarUsuario(id);

        if (usuarioRequestRecord.senha() == null || usuarioRequestRecord.senha().equals("")) {
            throw new EmptyCredentialsException("Não foi possível atualizar, pois a senha está vazia!");
        }

        var senhaHash = passwordEncoder.encode(usuarioRequestRecord.senha());
        usuarioAtualizado.setSenha(senhaHash);
        usuarioRepository.save(usuarioAtualizado);
        return usuarioAtualizado;
    }




}